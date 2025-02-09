# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Accept JAR_FILE as a build argument
ARG JAR_FILE

# Copy the packaged JAR file from the target folder into the container
COPY ./target/${JAR_FILE} app.jar

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "/app/app.jar"]