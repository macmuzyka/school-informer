FROM openjdk:17-jdk-slim

WORKDIR /app
RUN mkdir -p config
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

COPY target/*.jar informer-application.jar
COPY src/main/resources/application-secrets.properties /config/application-secrets.properties
COPY src/main/resources/application.properties /config/application.properties
COPY wait_for_kafka_and_postgres_db.sh wait_for_kafka_and_postgres_db.sh

RUN chmod +x wait_for_kafka_and_postgres_db.sh

EXPOSE 9001
ENTRYPOINT ["./wait_for_kafka_and_postgres_db.sh"]