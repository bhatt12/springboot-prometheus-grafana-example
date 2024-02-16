FROM ukgartifactory.pe.jfrog.io/images/base/openjdk-jre:17

WORKDIR /app

COPY build/libs/influxdb_datadog_example-0.0.1-SNAPSHOT.jar ./app1.jar

ENTRYPOINT exec java $JAVA_OPTS -jar ./app1.jar
