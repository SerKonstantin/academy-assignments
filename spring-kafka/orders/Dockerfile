# Последняя версия от ChatGPT

# Use Maven to build the app
FROM maven:3.9.5-eclipse-temurin-17 as builder

# Set working directory
WORKDIR /app

# Copy the parent POM to resolve dependencies
COPY ../pom.xml ../
COPY ./pom.xml .

# Copy the module's source code
COPY ./src ./src

# Build the application
RUN mvn -f ../pom.xml clean package -DskipTests

# Use a lightweight JRE for the final image
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/orders-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]




# Версия сгенеренная Amplicode

# FROM maven:latest AS builder
# WORKDIR /application
# COPY . .
# RUN --mount=type=cache,target=/root/.m2  mvn clean install -Dmaven.test.skip
#
# FROM eclipse-temurin:17-jre-alpine AS layers
# WORKDIR /application
# COPY --from=builder /application/target/*.jar app.jar
# RUN java -Djarmode=layertools -jar app.jar extract
#
# FROM eclipse-temurin:17-jre-alpine
# VOLUME /tmp
# RUN adduser -S spring-user
# USER spring-user
# COPY --from=layers /application/dependencies/ ./
# COPY --from=layers /application/spring-boot-loader/ ./
# COPY --from=layers /application/snapshot-dependencies/ ./
# COPY --from=layers /application/application/ ./
#
# ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]




# Рабочая версия, все запускается если jar сбилдить заранее

# FROM eclipse-temurin:17-jre-alpine
# WORKDIR /app
# COPY target/*.jar app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]
