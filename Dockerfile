FROM java:11
EXPOSE 8080
ENV ACTIVE_PROFILE=dev
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "/app.jar"]