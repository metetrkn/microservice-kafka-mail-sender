#!/bin/bash
cd /home/ec2-user/shop

# Build the application
mvn clean package -DskipTests

# Start the application
nohup java -jar target/shop-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
echo $! > shop.pid 