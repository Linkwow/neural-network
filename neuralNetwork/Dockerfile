# Build application stage
FROM maven:3.8.6-eclipse-temurin-11-focal AS app
COPY neural-core/src home/neural-network/lib/src
COPY neural-core/pom.xml home/neural-network/lib
WORKDIR home/neural-network/lib
RUN ["mvn", "clean", "install"]

COPY application/src home/neural-network/app/src
COPY application/pom.xml home/neural-network/app
WORKDIR home/neural-network/app
RUN ["mvn", "clean", "install"]

#Run application stage
FROM openjdk:11.0-jre
COPY --from=app root/.m2/repository/com/projects/naumenko/application/0.0.1-SNAPSHOT/application-0.0.1-SNAPSHOT.jar home/app/app.jar
ENTRYPOINT ["java", "-jar", "home/app/app.jar"]
EXPOSE 8080