#!/bin/bash
# Wait for the application to start
sleep 30

# Check if the application is running
if [ -f /home/ec2-user/shop/shop.pid ]; then
    pid=$(cat /home/ec2-user/shop/shop.pid)
    if ps -p $pid > /dev/null; then
        # Check if the application is responding
        if curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
            echo "Application started successfully"
            exit 0
        fi
    fi
fi

echo "Application failed to start"
exit 1 