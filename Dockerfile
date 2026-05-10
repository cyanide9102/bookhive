# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
ARG SERVICE_NAME

# Copy Infrastructure
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

COPY pom.xml .

# Satisfy the Maven Reactor map
COPY Common/pom.xml Common/
COPY DiscoveryService/pom.xml DiscoveryService/
COPY GatewayService/pom.xml GatewayService/
COPY IdentityService/pom.xml IdentityService/
COPY CatalogService/pom.xml CatalogService/
COPY OrderService/pom.xml OrderService/

# Copy source for the Library and the target Service
COPY Common/src Common/src
COPY ${SERVICE_NAME}/src ${SERVICE_NAME}/src

# Build the specific service and its dependencies
RUN ./mvnw clean package -pl ./Common,./${SERVICE_NAME} -am -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
ARG SERVICE_NAME

COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
