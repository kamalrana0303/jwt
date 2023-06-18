#FROM maven:3.9-eclipse-temurin-17 as build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:resolve
#COPY src src
#RUN mvn package
#
#FROM tomcat:10
#COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar ${CATALINA_HOME}/webapps/project-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["catalina.sh", "run"]