#!/bin/sh

wait_for() {
  echo "Checking $1 to listen port $2..."
  while ! nc -z $1 $2; do
    echo "sleep to waiting..."
    sleep 10s
  done
}

wait_for oem-nms-admin 8091
wait_for oem-nms-manager 8091
wait_for oem-nms-websocket 8093

java -jar /app.jar
