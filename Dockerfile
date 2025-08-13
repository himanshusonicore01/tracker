## Step 1: Use an OpenJDK base image
#FROM eclipse-temurin:17-jdk-alpine
#
## Step 2: Set a working directory in the container
#WORKDIR /app
#
## Step 3: Copy the built JAR file into the container
#COPY target/locationTracker-0.0.1-SNAPSHOT.jar app.jar
#
## Step 4: Expose the application port
#EXPOSE 8080
#
## Step 5: Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.8.4-openjdk-17 as build
LABEL maintainer="himanshu@coreteams.us"

RUN mkdir /home/src
COPY . /home/app/
WORKDIR /home/app

# Build the application
RUN mvn clean package

# -------- Run Stage --------
FROM openjdk:17-jdk-slim

# Create log directory in container
RUN mkdir -p /logs

WORKDIR /home/app

# Copy the built JAR from the build stage
COPY --from=build /home/app/target/locationTracker-0.0.1-SNAPSHOT.jar app.jar

# Define a mount point for external logs
VOLUME ["/logs"]

# Expose port
EXPOSE 8080

# Set active Spring profile (optional)
ENV SPRING_PROFILES_ACTIVE=default

# Run the JAR
CMD ["java", "-XX:+UseG1GC", "-jar", "app.jar"]
