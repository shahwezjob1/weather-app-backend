# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Accept JAR_FILE as a build argument
ARG JAR_FILE

# Copy the javaagent JAR file into the container
COPY ./opentelemetry-javaagent-2.12.0.jar opentelemetry-javaagent.jar

# Copy the packaged JAR file from the target folder into the container
COPY ./target/${JAR_FILE} app.jar

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "/app/app.jar"]