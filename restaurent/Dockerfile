# Use an official Java runtime as a parent image
FROM amazoncorretto:21.0.4-alpine3.18
# Set the working directory
WORKDIR /app

# Copy the application jar file to the container
COPY target/*.jar app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Run the application as a Java process
ENTRYPOINT ["java", "-jar", "app.jar"]
