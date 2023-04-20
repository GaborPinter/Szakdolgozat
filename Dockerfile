# Spring Boot alkalmazás
FROM openjdk:17-jdk-alpine
# WORKDIR /AUTOKER3
# COPY . .
# RUN mvn clean install
 ADD target/AUTOKER3.jar AUTOKER3.jar
 EXPOSE 9090
 ENTRYPOINT ["java","-jar","/AUTOKER3.jar"]

# CMD mvn spring-boot:run

# MySQL adatbázis
 FROM mysql:8.0 AS db
 ENV MYSQL_ROOT_PASSWORD mypassword
 ENV MYSQL_DATABASE mydatabase
 ENV MYSQL_USER myuser
 ENV MYSQL_PASSWORD mypassword
 EXPOSE 3306 
# COPY init.sql /docker-entrypoint-initdb.d