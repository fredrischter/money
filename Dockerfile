#
# Build
#
FROM maven:3.6.2-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package
#
FROM openjdk:8-jre-slim
WORKDIR /
ADD target/minimalist-1.0-SNAPSHOT-jar-with-dependencies.jar /minimalist-1.0-SNAPSHOT.jar
EXPOSE 4567
CMD java -jar minimalist-1.0-SNAPSHOT.jar