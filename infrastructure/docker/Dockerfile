# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
ARG SERVICE_NAME

COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -pl ${SERVICE_NAME} -am -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
ARG SERVICE_NAME

COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
