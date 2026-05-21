# BookHive

A Java-based microservices bookstore application built with **Spring Boot 3.5.14** and **Spring Cloud**. This project
implements a distributed e-commerce system with event-driven communication, JWT authentication, and Kafka-based service
orchestration.

---

## Services Overview

| Service          | Port | Description                                    |
|------------------|------|------------------------------------------------|
| DiscoveryService | 8761 | Netflix Eureka service registry                |
| GatewayService   | 8080 | API Gateway (OAuth2 JWT authentication)        |
| IdentityService  | 8081 | User registration, login, JWT token generation |
| CatalogService   | 5001 | Book and category management                   |
| OrderService     | 5002 | Order processing with Kafka event publishing   |
| PaymentService   | 5003 | Payment processing via Kafka consumers         |

---

## Technology Stack

- **Java 21** (LTS)
- **Spring Boot 3.5.14** / **Spring Cloud 2025.0.2**
- **Spring Cloud Netflix Eureka** (service discovery)
- **PostgreSQL 15** + **Hibernate/JPA**
- **H2 Database** (development/testing)
- **Apache Kafka 4.0.2** (event streaming)
- **OAuth2/JWT** with **jjwt 0.13.0**
- **Resilience4j** (circuit breakers)
- **OpenFeign** (declarative HTTP client)
- **MapStruct 1.5.5.Final** + **Lombok** (object mapping & boilerplate reduction)
- **hypersistence-tsid 2.1.4** (Time-Sorted Unique Identifier generation for distributed IDs)

---

## Architecture Patterns

### Shared Kernel

The `Common` module provides shared domain classes, events, Kafka topics definitions, and security annotations used
across all services. Includes packages for events, Kafka configuration, custom annotations, exceptions, and security
utilities.

### Event-Driven Communication

Services communicate asynchronously via Kafka topics. The Order flow triggers a payment event when an order is created,
enabling decoupled payment processing.

**Kafka Topics:**

| Topic             | Producer       | Consumer       | Description                        |
|-------------------|----------------|----------------|------------------------------------|
| order-created     | OrderService   | PaymentService | Triggered when an order is created |
| payment-completed | PaymentService | OrderService   | Triggered after successful payment |

---

## Building and Running

### Prerequisites

- **Docker** & **Docker Compose**
- **Java 21 SDK**
- **Maven Wrapper** (`mvnw` - located in project root only)

### Infrastructure Setup

Start the full infrastructure stack (PostgreSQL, Kafka, all services):

```bash
docker compose -f infrastructure/docker/docker-compose.yaml --env-file infrastructure/.env.dev up --build -d
```

Stop and clean all containers with volumes:

```bash
docker compose -f infrastructure/docker/docker-compose.yaml --env-file infrastructure/.env.dev down -v
```

**Infrastructure Components:**

- PostgreSQL 15 (port 5432)
- Apache Kafka 4.0.2 (port 9092)
- Discovery Service (port 8761)
- All microservices via Docker Compose

### Building Services

**Build the entire monorepo (from project root):**

```bash
./mvnw clean install -DskipTests
```

**Build a specific service (from project root):**

```bash
./mvnw clean package -pl <service-name> -am -DskipTests
```

### Running Services

**Run a specific service (from project root):**

```bash
./mvnw -pl <service-name> spring-boot:run
```

### Testing

**Run unit tests for the entire project:**

```bash
./mvnw test
```

**Run all tests including integration tests:**

```bash
./mvnw clean test -DskipITs=false
```

---

## Development Conventions

### Code Style & Dependencies

All microservices follow these conventions (defined in individual `pom.xml` files):

**Build Plugins:**

- Spring Boot Maven Plugin - Repackage JARs for production
- Maven Compiler Plugin - Configure annotation processors for MapStruct/Lombok

**Core Dependencies:**

- MapStruct - Compile-time object mapping
- Lombok - Reduce boilerplate code (@Data, @Service, @RestController, etc.)
- hypersistence-tsid - Generate globally unique, time-sorted identifiers

### Service Structure Pattern

Each service follows a consistent package structure with:

- Application entry point
- Remote service clients (OpenFeign) for inter-service communication
- Configuration classes for service-specific settings
- Domain packages containing DTOs, services, repositories, and mappers
- Service-specific subpackages for domain entities (e.g., book, category, order, auth)

### Kafka Event Patterns

**Producer Pattern:** Events are published using Spring Kafka's `KafkaTemplate` with topic name and key.

**Consumer Pattern:** Services consume events using `@KafkaListener` annotations on handler methods.

### Security & Authentication

All protected endpoints require JWT authentication via the `@RequiresLogin` annotation. The GatewayService intercepts
all requests, validates JWT tokens, and forwards authenticated requests to backend services.

**Gateway Configuration:**

- JwtAuthenticationFilter handles token validation
- Eureka client for service discovery
- JWT secret and expiration configured per-service

### Environment Variables

Services read configuration from Docker Compose or local `.env` files:

| Variable                      | Description                       |
|-------------------------------|-----------------------------------|
| `DB_USER`                     | Database username                 |
| `DB_PASSWORD`                 | Database password                 |
| `DB_URL_<SERVICE>`            | Service-specific database URL     |
| `EUREKA_CLIENT_SERVICE_URL`   | Eureka discovery service URL      |
| `EUREKA_INSTANCE_HOSTNAME`    | Service hostname for registration |
| `JWT_SECRET`                  | JWT signing key                   |
| `JWT_ACCESS_TOKEN_EXPIRATION` | JWT token expiration time         |
| `H2_CONSOLE_ENABLED`          | Enable H2 console (dev only)      |

```env
# Network & Service Discovery
EUREKA_URL=http://discovery-service:8761/eureka/
EUREKA_PREFER_IP=true

# Eureka Client Specific URLs
EUREKA_HOSTNAME_IDENTITY=identity-service
EUREKA_HOSTNAME_CATALOG=catalog-service
EUREKA_HOSTNAME_ORDER=order-service
EUREKA_HOSTNAME_PAYMENT=payment-service

# Database Shared Config
DB_USER=
DB_PASSWORD=
DB_DRIVER=org.postgresql.Driver
DB_PLATFORM=org.hibernate.dialect.PostgreSQLDialect

# Database Specific URLs
DB_URL_IDENTITY=jdbc:postgresql://postgres-db:5432/identity_db
DB_URL_CATALOG=jdbc:postgresql://postgres-db:5432/catalog_db
DB_URL_ORDER=jdbc:postgresql://postgres-db:5432/order_db
DB_URL_PAYMENT=jdbc:postgresql://postgres-db:5432/payment_db

# H2 Console
H2_ENABLED=false

# Security
JWT_SECRET=
JWT_ACCESS_TOKEN_EXPIRATION=900000
```

---

## Key Files Reference

| File/Directory                              | Purpose                                                       |
|---------------------------------------------|---------------------------------------------------------------|
| `pom.xml` (root)                            | Multi-module Maven parent defining all modules                |
| `bookhive-postman.json`                     | Postman collection for API testing                            |
| `infrastructure/docker/docker-compose.yaml` | Orchestration configuration for all services + Kafka/Postgres |
| `infrastructure/docker/Dockerfile`          | Multi-stage build template (build → runtime)                  |
| `Common/pom.xml`                            | Shared module dependencies                                    |
| `<service>/src/main/java/*Application.java` | Spring Boot entry point for each service                      |

---

## Troubleshooting

### Common Issues

**Service not discovering:**

- Ensure DiscoveryService is running before other services
- Verify `EUREKA_CLIENT_SERVICE_URL` is correctly configured

**Kafka messages not being received:**

- Check that the consumer group ID matches in both producer and consumer
- Verify Kafka topics exist: `docker exec -it kafka kafka-topics --describe`

**JWT authentication failures:**

- Ensure `JWT_SECRET` is consistent across GatewayService and IdentityService
- Validate token expiration settings match client expectations

---

> **API Documentation:** For complete API endpoint documentation with request/response examples, refer to
`bookhive-postman.json`.
