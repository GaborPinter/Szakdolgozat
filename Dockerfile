FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/AUTOKER3-0.0.1-SNAPSHOT.jar AUTOKER3-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/AUTOKER3-0.0.1-SNAPSHOT.jar"]


