FROM openjdk:16
ADD target/incident-dashboard-0.0.1.jar incident-dashboard-0.0.1.jar
ENTRYPOINT exec java -jar incident-dashboard-0.0.1.jar