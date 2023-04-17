
# Spring Boot alkalmazás
FROM openjdk:17-jdk-alpine
ADD target/AUTOKER3-0.0.1-SNAPSHOT.jar AUTOKER3-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/AUTOKER3-0.0.1-SNAPSHOT.jar"]

# MySQL adatbázis
FROM mysql:8.0 AS db
ENV MYSQL_ROOT_PASSWORD mypassword
ENV MYSQL_DATABASE mydatabase
ENV MYSQL_USER myuser
ENV MYSQL_PASSWORD mypassword
EXPOSE 3306

# COPY init.sql /docker-entrypoint-initdb.d