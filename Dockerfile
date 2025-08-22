# Build stage
FROM gradle:8-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew ./
COPY src ./src
RUN ./gradlew build -x test

# Runtime stage
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/ParkingSystem-0.0.1-SNAPSHOT.jar /app/ParkingSystem.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/ParkingSystem.jar"]