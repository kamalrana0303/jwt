#FROM gradle-8.0-jdk17
#WORKDIR /app
#RUN chown -R gradle:gradle /app
#USER gradle
#COPY build.gradle .
#COPY src src
#RUN gradle buid
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","build/libs/project-0.0.1-SNAPSHOT.jar"]