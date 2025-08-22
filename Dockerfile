FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/ParkingSystem-0.0.1-SNAPSHOT.jar /app/ParkingSystem.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/ParkingSystem.jar"]