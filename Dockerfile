FROM openjdk:21
COPY target/application-1.0.0-SNAPSHOT.jar application-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/application-1.0.0-SNAPSHOT.jar"]