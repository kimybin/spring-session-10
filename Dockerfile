FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/spring-session-10-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]

