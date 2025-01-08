FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY orders/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]



# Версия для билда внутри контейнера
# При нескольких контейнерах работает очень медленно, лучше собирать jar заранее

# FROM maven:3.9.5-eclipse-temurin-17 AS builder
# WORKDIR /app
# COPY pom.xml .
# COPY orders/pom.xml orders/
# COPY orders/src orders/src
# RUN mvn -f orders/pom.xml clean package -DskipTests
#
# FROM eclipse-temurin:17-jre-alpine
# WORKDIR /app
# COPY --from=builder /app/orders/target/*.jar app.jar
# EXPOSE 8081
# ENTRYPOINT ["java", "-jar", "app.jar"]
