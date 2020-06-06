FROM oem/nms-java8:latest

ADD app.jar app.jar
ADD application.yml application.yml
ADD about.json about.json
ADD docker-entrypoint.sh docker-entrypoint.sh

RUN sh -c "touch /app.jar"
RUN sh -c "chmod +x /docker-entrypoint.sh"

ENTRYPOINT ["docker-entrypoint.sh", "nms-admin"]
