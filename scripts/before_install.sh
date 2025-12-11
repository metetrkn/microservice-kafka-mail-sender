#!/bin/bash
# Stop the application if it's running
if [ -f /home/ec2-user/shop/shop.pid ]; then
    kill $(cat /home/ec2-user/shop/shop.pid) || true
    rm /home/ec2-user/shop/shop.pid
fi

# Clean up the deployment directory
rm -rf /home/ec2-user/shop/* 