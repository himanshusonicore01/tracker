# Step 1: Use an OpenJDK base image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set a working directory in the container
WORKDIR /app

# Step 3: Copy the built JAR file into the container
COPY target/location-tracker.jar app.jar

# Step 4: Expose the application port
EXPOSE 1010

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
