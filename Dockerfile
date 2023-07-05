# Use a base image with Java 17 pre-installed
FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the entry point command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
