# Use a lightweight JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine as build

# Create app directory
WORKDIR /app

# Copy the jar built by Maven
COPY target/apicalendario-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
