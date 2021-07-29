FROM openjdk:16
COPY target/incident-dashboard-0.0.1-SNAPSHOT.jar incident-dashboard-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/incident-dashboard-0.0.1-SNAPSHOT.jar"]