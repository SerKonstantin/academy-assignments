FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY shipping/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
