#!/bin/bash
wait_for_service() {
  local host=$1
  local port=$2

  while ! nc -z $host $port; do
    echo "Waiting for 10 seconds for $host:$port..."
    sleep 10
  done
  echo "$host:$port is up!"
}

wait_for_service db 5432

wait_for_service kafka 9092

echo "Starting the application..."
exec java -jar *.jar
