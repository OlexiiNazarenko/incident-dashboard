FROM openjdk:11
ADD target/incident-dashboard-0.0.1-SNAPSHOT.jar incident-dashboard-0.0.1-SNAPSHOT.jar
ENTRYPOINT exec java -jar incident-dashboard-0.0.1-SNAPSHOT.jar