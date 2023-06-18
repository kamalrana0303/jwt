#FROM tomcat:10
#COPY ./target/project-0.0.1-SNAPSHOT.war ${CATALINA_HOME}/webapps/project-0.0.1-SNAPSHOT.war
#EXPOSE 8080
#ENTRYPOINT ["catalina.sh", "run"]

FROM maven:3.9-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

FROM tomcat:latest
COPY --from=build /app/target/project-0.0.1-SNAPSHOT.war ${CATALINA_HOME}/webapps/project-0.0.1-SNAPSHOT.war
RUN sh -c 'touch /usr/local/tomcat/webapps/project-0.0.1-SNAPSHOT.war'
#RUN ["apt-get", "update"]
#RUN ["apt-get", "-y", "install", "vim"]
EXPOSE 8080
EXPOSE 9090
#ENTRYPOINT ["catalina.sh", "run"]
ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/project-0.0.1-SNAPSHOT.war"]