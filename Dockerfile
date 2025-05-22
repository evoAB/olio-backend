# ----------- Stage 1: Build the JAR -----------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Build the project (skip tests to avoid failures)
RUN mvn clean package -DskipTests



# ----------- Stage 2: Run the JAR -----------
FROM openjdk:17-jdk-slim

# Set working directory for the app
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Command to run the app
CMD ["java", "-jar", "app.jar"]
