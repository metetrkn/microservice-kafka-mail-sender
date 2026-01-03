# RQueue Email-Message Processing Project
## Project Overview
This project benchmarks and analyzes the performance of RQueue as a message broker for scalable, reliable, and high-throughput email delivery systems. The goal is to identify optimal configuration parameters and architectural patterns for robust email processing using RQueue.
- Number of mails published in producer

---

## How to utilize
 1- Check the test cases in TEST-CASE/ to observe the parameters and their effects on performance.
 2- You may retest the same parameters—or try additional ones—to gather further insights.


---

## Project Structure

```
rqueu/
├── log-eval/
│   ├── log-analyzer.py
│   ├── log_report.csv
│   ├── state.json
│   └── visualize.py
├── rqueu-consumer/
│   ├── .idea/
│   ├── .mvn/
│   ├── HELP.md
│   ├── logs/
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src/
│   └── target/
├── rqueu-producer/
│   ├── .idea/
│   ├── .mvn/
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src/
│   └── target/
├── sharedDTO/
│   ├── .gitignore
│   ├── .idea/
│   ├── pom.xml
│   └── src/
├── TEST-CASE/
│   ├── TEST-1/
│   ├── TEST-2/
│   ├── TEST-3/
│   ├── TEST-4/
│   ├── TEST-5/
│   └── TEST-6/
└── README.md
```

---

**Logs:**
- Logs are located at rqueue-consumer/logs/email-consumer.log.
- The log-eval (Python) project retrieves these logs to generate outputs.

**Output:**
- Metrics and logs are stored in the results directory, including:
  - `log_report.csv`: Detailed performance metrics per queu
  - `report.png`: Visualization of performance data
  - `Result.txt`: Summary of configuration, analysis, and findings

---

## Insights & Best Practices
- **Concurrency Management:** 
 - Different ranges of concurrency per queue were tested, and as seen in the results (TEST-5/RESULT.txt and concurrency_analysis.png), the configuration of 10–20 concurrency for high-priority and 5–10 for low-priority provides the best overall performance—especially for high-priority emails, but also for low-priority ones.

 - Increasing concurrency does not always lead to better performance. As shown in concurrency_analysis.png, even though resources are doubled, the performance boost decreases once concurrency is set above 5–10 for standard emails.

 - Assigning too much concurrency to the low-priority queue steals resources from the high-priority queue; therefore, a balance between the queues is required.

 - When the workload ratio is approximately 1:10, the optimal concurrency allocation observed was 2/1 for high/low priority queues.

---

## System Architecture
### Producer
- By default, the producer publishes messages synchronously in Redis.
- There is no batching by default. This is the default behavior in Redis and in this project as well.
- Publishes email messages to RQueue queues asynchronously.
- Supports configurable batch sizes and message rates.
- Each message includes unique identifiers and payload data (from, to, subject, fake body message, created timestamp).
- There are 2 different type of subjects that mails can contain (STANDART and VIP)

### Consumer
- By default, consumers act asynchronously, racing to handle incoming messages from the queue.
- There is no batching by default. This is the default behavior in Redis and in this project as well.
- Multiple consumers/workers process messages in parallel from RQueue.
- Each message triggers an email send operation (WireMock - fake).
- Offsets and acknowledgments ensure reliable delivery.

### Shared DTO
 - The sharedDTO/ folder contains a file EmailDTO.java, which represents the schema of an email. It is used by both the producer and consumer via dependency injection.
 - The shared DTO is located in the .m2 folder, where Maven stores shared packages.
 - Run the command `mvn install` from the sharedDTO directory to generate the .class files.
 - After that, add the credentials from the pom.xml of the related import file—in this case, for both the producer and consumer.
---

## Configurations
1. In application.properties, keep rqueue.scheduler.auto-start=false; otherwise, stubbing may not work because the consumer could start listening to messages too early, causing errors or missed messages.
- Make sure to set the Redis and mail sender service WireMock host, port, API key, and URL appropriately.
2. rqueu-consumer/consumer/EmailConsumer.java: Set concurrencyHigh and concurrencyLow for different performance results.


## How to Run Tests
1. Ensure to set all configurations properly.
2. Start the producer to enqueue messages.
3. Launch consumers to process the queue. It is generally better to start consumers first, even though published messages wait in the Redis queue (RAM) until consumed. Be aware that if the system crashes while messages are still in RAM, data may be lost.
4. Analyze output metrics and logs in the results directory. (Run log-eval/log-analyzer.py and visualize.py)

---

## Results & Analysis
- Review `log_report.csv` and `Result.txt` for detailed performance data and conclusions.
- Use `report.png` for a visual summary of throughput and resource utilization.

---

## Prerequisites
- **Java SDK 17** and **Maven** for building and running producer/consumer modules.
- **Python 3.14** and related import libraries for log analysis.
- **Rqueu** cluster or docker running and accessible image redis:latest.
- **Email server** Wiremock Docker image wiremock/wiremock:latest
---

## Review Test Results
Check the `TEST RESULTS` folder for scenario-specific logs and documentation.

---

## License
This project is licensed under the MIT License. Copyright © 2026.

---

## Authors
- Mete Turkan
- metetrkn52@gmail.com
- https://www.linkedin.com/in/mete-turkan/
---