# Use an official Java runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Ensure the target directory exists before copying
RUN mkdir -p /app/target

# Copy the built JAR file from target directory
COPY target/real-estate-platform-1.0.0.jar app.jar

# Expose the application's port
EXPOSE 8080

# Set environment variables for Spring Boot
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/realestate_db
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_FLYWAY_ENABLED=true

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]