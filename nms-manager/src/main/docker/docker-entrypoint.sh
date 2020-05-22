#!/bin/sh

wait_for() {
  echo "Checking $1 to listen port $2..."
  while ! nc -z $1 $2; do
    echo "sleep to waiting..."
    sleep 10s
  done
}

java -jar /app.jar
