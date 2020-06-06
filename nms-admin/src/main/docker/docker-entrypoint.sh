#!/bin/sh

set -e

wait_for() {
  echo "Checking $1 to listen port $2..."
  while ! nc -z $1 $2; do
    echo "sleep to waiting..."
    sleep 10s
  done
}

wait_for oem-nms-mysql 3306
wait_for oem-nms-mongo 27017
wait_for oem-nms-rabbitmq 5672
wait_for oem-nms-redis 6379

java -jar /app.jar
