FROM eclipse-temurin:17
RUN mkdir /app
WORKDIR /app
COPY ./target/project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/project-0.0.1-SNAPSHOT.jar"]