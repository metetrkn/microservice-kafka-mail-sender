import re
import csv
from collections import defaultdict
import json
import os

STATE_FILE = 'state.json'

def get_report_id():
    if not os.path.exists(STATE_FILE):
        with open(STATE_FILE, 'w') as f:
            json.dump({'report_count': 0}, f)

    with open(STATE_FILE, 'r') as f:
        try:
            state = json.load(f)
        except json.JSONDecodeError:
            state = {'report_count': 0}

    current_count = state.get('report_count', 0)
    new_count = current_count + 1
    state['report_count'] = new_count

    with open(STATE_FILE, 'w') as f:
        json.dump(state, f, indent=4)
        
    return current_count


def analyze_logs_to_csv(input_file, output_file):
    # Regex handles the "Sent to: email |" section
    log_pattern = re.compile(
        r"Topic:\s*(?P<topic>[^|]+)\s*\|\s*"
        r"Consumer:\s*(?P<consumer>[^|]+)\s*\|\s*"
        r"Sent to:\s*[^|]+\s*\|\s*"
        r"Created:\s*(?P<created>\d+)\s*\|\s*"
        r"Sent:\s*(?P<sent>\d+)"
    )

    stats = defaultdict(list)
    report_id = get_report_id()
    
    # [NEW] Initialize error counter
    total_errors = 0

    try:
        # 1. Read and Parse
        with open(input_file, 'r', encoding='utf-8') as f:
            for line in f:
                # [NEW] Check for ERROR simply by string matching
                if "ERROR" in line:
                    total_errors += 1

                # Check for successful send log
                match = log_pattern.search(line)
                if match:
                    topic = match.group('topic').strip()
                    consumer = match.group('consumer').strip()
                    created_ts = int(match.group('created'))
                    sent_ts = int(match.group('sent'))

                    exec_time_ms = sent_ts - created_ts
                    stats[(topic, consumer)].append(exec_time_ms)

        # 2. CALCULATE GRAND TOTAL (Sum of all emails in this run)
        grand_total_mails = sum(len(times) for times in stats.values())

        # 3. Write to CSV
        file_exists = os.path.isfile(output_file) and os.path.getsize(output_file) > 0

        with open(output_file, 'a', newline='', encoding='utf-8') as csvfile:
            writer = csv.writer(csvfile)

            # HEADER: Added 'total_errors' to the end
            if not file_exists:
                writer.writerow(['report_id', 'topic', 'consumer', 'total_mails', 'average_execution_time_(s)', 'max_execution_time_(s)', 'grand_total_mails', 'total_errors'])

            # If stats is empty (only errors occurred), we won't enter this loop.
            # But usually, you want at least one row or you skip writing. 
            # If you want to record a row even if only errors happened, you'd need logic for that.
            # For now, this writes rows only if successful mails exist.
            for (topic, consumer), times_ms in stats.items():
                total_count = len(times_ms)
                
                avg_time_ms = sum(times_ms) / total_count
                max_time_ms = max(times_ms)

                avg_time_s = avg_time_ms / 1000.0
                max_time_s = max_time_ms / 1000.0
                
                # ROW: Adding 'total_errors' variable to the end of the list
                writer.writerow([
                    report_id, 
                    topic, 
                    consumer, 
                    total_count, 
                    f"{avg_time_s:.2f}", 
                    f"{max_time_s:.2f}",
                    grand_total_mails,
                    total_errors  # <--- THIS WRITES THE ERROR COUNT
                ])

        print(f"Success! Report updated: {output_file} (Total Errors: {total_errors})")

    except FileNotFoundError:
        print(f"Error: The file '{input_file}' was not found.")

if __name__ == "__main__":
    analyze_logs_to_csv('C:/Users/mete/Desktop/staj/test-case/test/javaConsumer/javaConsumer/logs/email-consumer.log',
                        'log_report.csv')