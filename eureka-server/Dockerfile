# Stage 1: Build the application
FROM gradle:8.4-jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle bootJar --no-daemon

# Stage 2: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
