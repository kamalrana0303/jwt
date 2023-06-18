#FROM maven:3.9-eclipse-temurin-17 as build
#WORKDIR /app
#COPY pom.xml .
#COPY src src
#RUN --mount=type=cache,target=/root/.m2 mvn package
#
#FROM tomcat:10
#COPY --from=build /app/target/project-0.0.1-SNAPSHOT.war ${CATALINA_HOME}/webapps/ROOT.war
#EXPOSE 8080
#ENTRYPOINT ["catalina.sh", "run"]