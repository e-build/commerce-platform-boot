FROM openjdk:11-jre-slim
EXPOSE 8080
ENV ACTIVE_PROFILE=real
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "/app.jar"]