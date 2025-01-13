FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY notifications/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
