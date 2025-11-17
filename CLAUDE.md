# CLAUDE.md - AI Assistant Guide for AlertaVecinalBackend

**Last Updated**: 2025-11-17
**Project**: AlertaVecinalBackend
**License**: Apache 2.0
**Primary Language**: Kotlin
**Architecture**: Hexagonal (Ports & Adapters)
**Framework**: Ktor

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Current Repository State](#current-repository-state)
3. [Technology Stack](#technology-stack)
4. [Directory Structure](#directory-structure)
5. [Development Workflows](#development-workflows)
6. [Coding Conventions](#coding-conventions)
7. [Git Workflow](#git-workflow)
8. [Testing Strategy](#testing-strategy)
9. [API Design Principles](#api-design-principles)
10. [Database Guidelines](#database-guidelines)
11. [Security Best Practices](#security-best-practices)
12. [Common Commands](#common-commands)
13. [AI Assistant Guidelines](#ai-assistant-guidelines)

---

## Project Overview

**AlertaVecinalBackend** is a backend service for the Alerta Vecinal (Neighborhood Alert) application. The system is designed to manage neighborhood alerts, notifications, and community safety features.

### Purpose
- Provide RESTful API endpoints for mobile/web clients
- Manage user accounts and neighborhood communities
- Handle alert creation, distribution, and management
- Enable real-time notifications for neighborhood events
- Support geolocation-based alert filtering

### Target Architecture
- **Type**: REST API Backend Service
- **Deployment**: Cloud-ready (containerized)
- **Scalability**: Designed for horizontal scaling
- **Data**: Relational database with spatial capabilities

---

## Current Repository State

**⚠️ IMPORTANT**: This repository is in its **initial scaffold phase**.

### What Exists
- ✅ Git repository initialized
- ✅ Apache 2.0 License
- ✅ Basic README.md
- ✅ .gitignore configured for Java/Kotlin projects

### What Needs Implementation
- ❌ Build configuration (Gradle/Maven)
- ❌ Source code directories
- ❌ Framework setup (Spring Boot expected)
- ❌ Database configuration
- ❌ API endpoints
- ❌ Testing framework
- ❌ CI/CD pipeline
- ❌ Docker containerization
- ❌ Documentation

### Initial Setup Required

When implementing the first features, establish:

1. **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`)
2. **Framework**: Ktor 2.x
3. **Language**: Kotlin
4. **Architecture**: Hexagonal (Ports & Adapters)
5. **Dependency Injection**: Koin
6. **Database**: PostgreSQL with PostGIS extension (for geospatial features)
7. **ORM**: Exposed (Kotlin SQL framework)
8. **API Format**: REST API

---

## Technology Stack

### Core Technologies

#### Backend Framework
- **Ktor 2.x** - Asynchronous web framework for Kotlin
- **Ktor Server** - HTTP server with Netty engine
- **Ktor Routing** - REST API routing
- **Ktor Content Negotiation** - JSON serialization/deserialization
- **Ktor Authentication** - JWT-based authentication
- **Ktor Validation** - Request validation

#### Dependency Injection
- **Koin** - Lightweight dependency injection for Kotlin
- No annotations, pure Kotlin DSL
- Constructor injection pattern

#### Database & Persistence
- **PostgreSQL 15+** - Primary relational database
- **PostGIS** - Geospatial extension for location-based queries
- **Exposed** - Kotlin SQL framework (DSL and DAO)
- **HikariCP** - High-performance connection pooling
- **Flyway** - Database migrations

#### Build & Dependency Management
- **Gradle 8.x** - Build automation
- **Kotlin DSL** - Build script language (`build.gradle.kts`)
- **Gradle Version Catalog** - Centralized dependency management

#### Testing
- **JUnit 5** - Unit testing framework
- **Kotest** - Kotlin-first testing framework
- **MockK** - Mocking library for Kotlin
- **Testcontainers** - Integration testing with Docker
- **Ktor Test** - Ktor application testing utilities
- **Kover** - Kotlin code coverage

#### Serialization & Validation
- **kotlinx.serialization** - Kotlin native JSON serialization
- **Kotlinx DateTime** - Date/time handling
- **Konform** - Type-safe validation for Kotlin

#### Logging & Monitoring
- **SLF4J + Logback** - Logging framework
- **Ktor CallLogging** - HTTP request/response logging
- **Micrometer** - Metrics collection
- **Prometheus** - Metrics monitoring

#### DevOps & Tools
- **Docker** - Containerization
- **Docker Compose** - Local development environment
- **GitHub Actions** - CI/CD pipeline

---

## Directory Structure

### Hexagonal Architecture Layout

La arquitectura hexagonal (también conocida como Ports & Adapters) organiza el código en capas concéntricas:

- **Domain**: Lógica de negocio pura, sin dependencias externas
- **Application**: Casos de uso y orquestación (ports)
- **Infrastructure**: Implementaciones concretas (adapters)

```
AlertaVecinalBackend/
├── .github/
│   └── workflows/              # GitHub Actions CI/CD
│       ├── build.yml
│       ├── test.yml
│       └── deploy.yml
├── .gradle/                    # Gradle cache (gitignored)
├── build/                      # Build output (gitignored)
├── gradle/
│   └── libs.versions.toml      # Version catalog
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/alertavecinal/
│   │   │       ├── Application.kt              # Ktor application entry point
│   │   │       │
│   │   │       ├── domain/                     # DOMAIN LAYER (núcleo)
│   │   │       │   ├── model/                  # Entidades de dominio
│   │   │       │   │   ├── Alert.kt
│   │   │       │   │   ├── User.kt
│   │   │       │   │   ├── Neighborhood.kt
│   │   │       │   │   ├── Comment.kt
│   │   │       │   │   └── valueobjects/       # Value Objects
│   │   │       │   │       ├── Location.kt
│   │   │       │   │       ├── AlertCategory.kt
│   │   │       │   │       └── AlertSeverity.kt
│   │   │       │   ├── exception/              # Domain exceptions
│   │   │       │   │   ├── DomainException.kt
│   │   │       │   │   ├── AlertNotFoundException.kt
│   │   │       │   │   └── InvalidLocationException.kt
│   │   │       │   └── event/                  # Domain events
│   │   │       │       ├── AlertCreated.kt
│   │   │       │       ├── AlertConfirmed.kt
│   │   │       │       └── AlertResolved.kt
│   │   │       │
│   │   │       ├── application/                # APPLICATION LAYER (casos de uso)
│   │   │       │   ├── port/                   # Puertos (interfaces)
│   │   │       │   │   ├── input/              # Puertos de entrada (use cases)
│   │   │       │   │   │   ├── CreateAlertUseCase.kt
│   │   │       │   │   │   ├── GetNearbyAlertsUseCase.kt
│   │   │       │   │   │   ├── ConfirmAlertUseCase.kt
│   │   │       │   │   │   ├── ResolveAlertUseCase.kt
│   │   │       │   │   │   └── RegisterUserUseCase.kt
│   │   │       │   │   └── output/             # Puertos de salida (repositories, services)
│   │   │       │   │       ├── AlertRepository.kt
│   │   │       │   │       ├── UserRepository.kt
│   │   │       │   │       ├── NeighborhoodRepository.kt
│   │   │       │   │       ├── NotificationService.kt
│   │   │       │   │       ├── GeolocationService.kt
│   │   │       │   │       └── EventPublisher.kt
│   │   │       │   └── service/                # Implementación de casos de uso
│   │   │       │       ├── AlertService.kt
│   │   │       │       ├── UserService.kt
│   │   │       │       ├── NeighborhoodService.kt
│   │   │       │       └── NotificationOrchestrator.kt
│   │   │       │
│   │   │       └── infrastructure/             # INFRASTRUCTURE LAYER (adaptadores)
│   │   │           ├── adapter/
│   │   │           │   ├── input/              # Adaptadores de entrada
│   │   │           │   │   ├── rest/           # REST API (HTTP adapter)
│   │   │           │   │   │   ├── route/      # Ktor routes
│   │   │           │   │   │   │   ├── AlertRoutes.kt
│   │   │           │   │   │   │   ├── UserRoutes.kt
│   │   │           │   │   │   │   ├── NeighborhoodRoutes.kt
│   │   │           │   │   │   │   └── AuthRoutes.kt
│   │   │           │   │   │   ├── dto/        # Request/Response DTOs
│   │   │           │   │   │   │   ├── request/
│   │   │           │   │   │   │   │   ├── CreateAlertRequest.kt
│   │   │           │   │   │   │   │   ├── UpdateAlertRequest.kt
│   │   │           │   │   │   │   │   └── RegisterUserRequest.kt
│   │   │           │   │   │   │   └── response/
│   │   │           │   │   │   │       ├── AlertResponse.kt
│   │   │           │   │   │   │       ├── UserResponse.kt
│   │   │           │   │   │   │       └── ErrorResponse.kt
│   │   │           │   │   │   └── mapper/     # DTO mappers
│   │   │           │   │   │       ├── AlertMapper.kt
│   │   │           │   │   │       └── UserMapper.kt
│   │   │           │   │   └── cli/            # CLI adapter (futuro)
│   │   │           │   └── output/             # Adaptadores de salida
│   │   │           │       ├── persistence/    # Database adapter
│   │   │           │       │   ├── entity/     # Database entities (Exposed)
│   │   │           │       │   │   ├── AlertEntity.kt
│   │   │           │       │   │   ├── UserEntity.kt
│   │   │           │       │   │   └── NeighborhoodEntity.kt
│   │   │           │       │   ├── repository/ # Repository implementations
│   │   │           │       │   │   ├── PostgresAlertRepository.kt
│   │   │           │       │   │   ├── PostgresUserRepository.kt
│   │   │           │       │   │   └── PostgresNeighborhoodRepository.kt
│   │   │           │       │   └── mapper/     # Entity-Domain mappers
│   │   │           │       │       ├── AlertEntityMapper.kt
│   │   │           │       │       └── UserEntityMapper.kt
│   │   │           │       ├── notification/   # Notification adapter
│   │   │           │       │   ├── PushNotificationService.kt
│   │   │           │       │   └── EmailNotificationService.kt
│   │   │           │       ├── geolocation/    # Geolocation adapter
│   │   │           │       │   └── PostGISGeolocationService.kt
│   │   │           │       └── event/          # Event publisher adapter
│   │   │           │           └── InMemoryEventPublisher.kt
│   │   │           └── config/                 # Configuration
│   │   │               ├── DatabaseConfig.kt
│   │   │               ├── KoinConfig.kt       # Dependency injection setup
│   │   │               ├── RoutingConfig.kt
│   │   │               ├── SecurityConfig.kt
│   │   │               └── SerializationConfig.kt
│   │   └── resources/
│   │       ├── application.conf                # Ktor configuration (HOCON)
│   │       ├── logback.xml                     # Logging config
│   │       └── db/migration/                   # Flyway migrations
│   │           ├── V1__create_users_table.sql
│   │           ├── V2__create_neighborhoods_table.sql
│   │           ├── V3__create_alerts_table.sql
│   │           └── V4__create_spatial_indexes.sql
│   └── test/
│       ├── kotlin/
│       │   └── com/alertavecinal/
│       │       ├── domain/                     # Domain tests
│       │       │   └── model/
│       │       ├── application/                # Use case tests
│       │       │   └── service/
│       │       └── infrastructure/             # Integration tests
│       │           ├── adapter/
│       │           │   ├── rest/               # API tests
│       │           │   └── persistence/        # Repository tests
│       │           └── ApplicationTest.kt      # End-to-end tests
│       └── resources/
│           ├── application-test.conf
│           └── logback-test.xml
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── docs/
│   ├── architecture/                           # Architecture diagrams
│   │   ├── hexagonal-architecture.md
│   │   └── component-diagram.png
│   ├── api/                                    # API documentation
│   │   └── openapi.yaml
│   └── setup/                                  # Setup guides
│       └── local-development.md
├── scripts/
│   ├── setup-local-db.sh
│   └── run-tests.sh
├── .gitignore
├── .editorconfig                               # Code style config
├── build.gradle.kts                            # Gradle build file
├── settings.gradle.kts                         # Gradle settings
├── gradlew                                     # Gradle wrapper (Unix)
├── gradlew.bat                                 # Gradle wrapper (Windows)
├── CLAUDE.md                                   # This file (technical guide)
├── FEATURES.md                                 # Business domain documentation
├── README.md                                   # User documentation
└── LICENSE                                     # Apache 2.0 License
```

### Explicación de Capas

#### Domain Layer (Núcleo - sin dependencias externas)
- **Entities**: Modelos de dominio con lógica de negocio
- **Value Objects**: Objetos inmutables que representan conceptos del dominio
- **Domain Events**: Eventos importantes del dominio
- **Domain Exceptions**: Excepciones de negocio

#### Application Layer (Casos de uso)
- **Input Ports**: Interfaces de casos de uso (lo que la aplicación puede hacer)
- **Output Ports**: Interfaces de servicios externos necesarios (repositories, etc.)
- **Services**: Implementación de los casos de uso, orquestación de dominio

#### Infrastructure Layer (Implementaciones concretas)
- **Input Adapters**: REST API (Ktor), CLI, etc.
- **Output Adapters**: PostgreSQL, notificaciones, geolocalización, etc.
- **Configuration**: Setup de Koin, Ktor, base de datos, etc.

### Flujo de Dependencias

```
HTTP Request → REST Adapter → Use Case (Application) → Domain Logic → Repository Port → DB Adapter → PostgreSQL
                   ↓                                                            ↓
               DTO Mapper                                                Entity Mapper
```

**Regla de Oro**: Las dependencias apuntan hacia adentro. El dominio no conoce la infraestructura.

---

## Development Workflows

### Setting Up Local Environment

```bash
# 1. Clone the repository
git clone <repository-url>
cd AlertaVecinalBackend

# 2. Start local database (Docker Compose)
docker-compose up -d postgres

# 3. Build the project
./gradlew build

# 4. Run tests
./gradlew test

# 5. Start the application
./gradlew run

# 6. Access the API
curl http://localhost:8080/health
```

### Development Cycle

1. **Create Feature Branch**
   ```bash
   git checkout -b feature/alert-management
   ```

2. **Implement Feature**
   - Write failing test first (TDD)
   - Implement business logic
   - Create REST endpoint
   - Update API documentation

3. **Run Quality Checks**
   ```bash
   ./gradlew test           # Run tests
   ./gradlew detekt         # Code quality (if configured)
   ./gradlew jacocoTestReport  # Coverage report
   ```

4. **Commit and Push**
   ```bash
   git add .
   git commit -m "feat: add alert management endpoints"
   git push origin feature/alert-management
   ```

5. **Create Pull Request**
   - Ensure CI passes
   - Request code review
   - Address feedback
   - Merge to main

---

## Coding Conventions

### Package Naming
- Base package: `com.alertavecinal`
- Organize by architectural layer: `domain`, `application`, `infrastructure`
- No prefijos como `impl`, `dto` en nombres de paquetes
- Ejemplo: `com.alertavecinal.domain.model`, `com.alertavecinal.application.port.input`

### Class Naming - Arquitectura Hexagonal

#### Domain Layer
- **Entities**: Nombres de dominio (e.g., `Alert`, `User`, `Neighborhood`)
- **Value Objects**: Descriptivos (e.g., `Location`, `AlertCategory`)
- **Domain Events**: Pasado (e.g., `AlertCreated`, `UserRegistered`)
- **Domain Exceptions**: `*Exception` (e.g., `AlertNotFoundException`)

#### Application Layer
- **Use Cases (Input Ports)**: `*UseCase` (e.g., `CreateAlertUseCase`)
- **Repository Ports (Output)**: `*Repository` (e.g., `AlertRepository`)
- **Service Ports (Output)**: `*Service` (e.g., `NotificationService`)
- **Use Case Implementations**: `*Service` (e.g., `AlertService`)

#### Infrastructure Layer
- **REST Routes**: `*Routes` (e.g., `AlertRoutes`, `UserRoutes`)
- **DTOs**: `*Request`, `*Response` (e.g., `CreateAlertRequest`)
- **Mappers**: `*Mapper` (e.g., `AlertMapper`, `UserMapper`)
- **Repository Implementations**: `Postgres*Repository` (e.g., `PostgresAlertRepository`)
- **Database Entities**: `*Entity` (e.g., `AlertEntity`)
- **Configurations**: `*Config` (e.g., `DatabaseConfig`, `KoinConfig`)

### Code Style (Kotlin)
- **Indentation**: 4 spaces (no tabs)
- **Line Length**: 120 characters max
- **Braces**: K&R style (opening brace on same line for functions)
- **Imports**: No wildcards (`*`), organize automatically
- **Naming**:
  - Variables/functions: `camelCase`
  - Classes/interfaces: `PascalCase`
  - Constants: `UPPER_SNAKE_CASE`
  - Private properties: `camelCase` (sin prefijo `_`)

### Kotlin Best Practices
- ✅ Use `data class` for DTOs and Value Objects
- ✅ Prefer `val` over `var` (immutability)
- ✅ Use nullable types explicitly (`?`)
- ✅ Leverage extension functions for readability
- ✅ Use `when` instead of long `if-else` chains
- ✅ Use sealed classes for representing restricted hierarchies
- ✅ Prefer expression bodies for single-expression functions
- ✅ Use type aliases for complex types
- ✅ Leverage scope functions (`let`, `run`, `apply`, `also`, `with`)
- ✅ Use inline classes for type-safe primitives
- ❌ Avoid `!!` (non-null assertion operator) - handle nulls explicitly
- ❌ No Java-style getters/setters (use Kotlin properties)
- ❌ Avoid mutable collections in public APIs

### Dependency Injection with Koin

```kotlin
// Module definition
val domainModule = module {
    single<AlertRepository> { PostgresAlertRepository(get()) }
    factory<CreateAlertUseCase> { AlertService(get(), get()) }
}

// Usage in Ktor route
fun Route.alertRoutes() {
    val createAlertUseCase: CreateAlertUseCase by inject()

    post("/alerts") {
        val request = call.receive<CreateAlertRequest>()
        // ...
    }
}
```

### Ktor Routing Conventions

```kotlin
// Separate routes by resource in different files
fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            alertRoutes()
            userRoutes()
            neighborhoodRoutes()
        }
    }
}

// AlertRoutes.kt
fun Route.alertRoutes() {
    route("/alerts") {
        get { /* List alerts */ }
        post { /* Create alert */ }

        route("/{id}") {
            get { /* Get alert by ID */ }
            put { /* Update alert */ }
            delete { /* Delete alert */ }
        }
    }
}
```

### Error Handling

```kotlin
// Domain exceptions
sealed class DomainException(message: String) : Exception(message)
class AlertNotFoundException(id: UUID) : DomainException("Alert not found: $id")

// Global exception handling in Ktor
install(StatusPages) {
    exception<DomainException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message))
    }
    exception<AlertNotFoundException> { call, cause ->
        call.respond(HttpStatusCode.NotFound, ErrorResponse(cause.message))
    }
}
```

---

## Git Workflow

### Branch Naming
- **Features**: `feature/short-description`
- **Bug Fixes**: `fix/bug-description`
- **Hotfixes**: `hotfix/critical-issue`
- **Claude Branches**: `claude/claude-md-*` (auto-generated)

### Commit Message Format

Follow Conventional Commits:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding/updating tests
- `chore`: Build/tooling changes
- `perf`: Performance improvements

**Examples**:
```bash
feat(alerts): add geolocation-based alert filtering
fix(auth): resolve JWT token expiration issue
docs(api): update OpenAPI specification for user endpoints
test(alerts): add integration tests for alert creation
```

### Branch Protection
- Main branch requires PR reviews
- All tests must pass
- Code coverage must not decrease
- No direct commits to main

---

## Testing Strategy

### Test Pyramid

1. **Unit Tests** (70%)
   - Test individual methods and classes
   - Mock external dependencies
   - Fast execution (<100ms per test)
   - Location: `src/test/kotlin/*/unit/`

2. **Integration Tests** (20%)
   - Test database interactions
   - Test API endpoints with Testcontainers
   - Test external service integrations
   - Location: `src/test/kotlin/*/integration/`

3. **End-to-End Tests** (10%)
   - Test complete user workflows
   - Use real database (Testcontainers)
   - Test authentication flows

### Test Naming
```kotlin
// Pattern: methodName_scenario_expectedResult
fun createAlert_withValidData_returnsCreatedStatus()
fun findAlertsByLocation_withInvalidCoordinates_throwsValidationException()
```

### Code Coverage
- **Minimum**: 80% line coverage
- **Target**: 90% line coverage
- **Critical paths**: 100% coverage required

### Running Tests
```bash
# All tests
./gradlew test

# Unit tests only
./gradlew test --tests "*unit*"

# Integration tests only
./gradlew test --tests "*integration*"

# Single test class
./gradlew test --tests "AlertServiceTest"

# Coverage report
./gradlew jacocoTestReport
# Report: build/reports/jacoco/test/html/index.html
```

---

## API Design Principles

### RESTful Conventions

#### Resource Naming
- Use plural nouns: `/api/alerts`, `/api/users`
- Use kebab-case for multi-word: `/api/alert-categories`
- Version API: `/api/v1/alerts`

#### HTTP Methods
- **GET**: Retrieve resources (safe, idempotent)
- **POST**: Create new resources
- **PUT**: Replace entire resource (idempotent)
- **PATCH**: Partial update
- **DELETE**: Remove resource (idempotent)

#### Status Codes
- **200 OK**: Successful GET, PUT, PATCH
- **201 Created**: Successful POST
- **204 No Content**: Successful DELETE
- **400 Bad Request**: Validation error
- **401 Unauthorized**: Missing/invalid authentication
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource doesn't exist
- **409 Conflict**: Business rule violation
- **500 Internal Server Error**: Server error

### Endpoint Examples

```
# Alerts
GET    /api/v1/alerts                    # List all alerts
GET    /api/v1/alerts/{id}               # Get specific alert
POST   /api/v1/alerts                    # Create new alert
PUT    /api/v1/alerts/{id}               # Update alert
DELETE /api/v1/alerts/{id}               # Delete alert
GET    /api/v1/alerts?lat=X&lng=Y&radius=Z  # Filter by location

# Users
GET    /api/v1/users/{id}                # Get user profile
PATCH  /api/v1/users/{id}                # Update profile
GET    /api/v1/users/{id}/alerts         # User's alerts

# Neighborhoods
GET    /api/v1/neighborhoods             # List neighborhoods
GET    /api/v1/neighborhoods/{id}/alerts # Alerts in neighborhood
```

### Request/Response Format

**Request (POST /api/v1/alerts)**:
```json
{
  "title": "Suspicious Activity",
  "description": "Unknown person near the park",
  "category": "SECURITY",
  "location": {
    "latitude": -34.603722,
    "longitude": -58.381592,
    "address": "Av. Corrientes 1234, Buenos Aires"
  },
  "severity": "MEDIUM"
}
```

**Response (201 Created)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Suspicious Activity",
  "description": "Unknown person near the park",
  "category": "SECURITY",
  "location": {
    "latitude": -34.603722,
    "longitude": -58.381592,
    "address": "Av. Corrientes 1234, Buenos Aires"
  },
  "severity": "MEDIUM",
  "status": "ACTIVE",
  "createdBy": "user123",
  "createdAt": "2025-11-16T10:30:00Z",
  "updatedAt": "2025-11-16T10:30:00Z"
}
```

**Error Response (400 Bad Request)**:
```json
{
  "timestamp": "2025-11-16T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "title",
      "message": "Title must not be empty"
    },
    {
      "field": "location.latitude",
      "message": "Latitude must be between -90 and 90"
    }
  ],
  "path": "/api/v1/alerts"
}
```

### Pagination

Use query parameters:
```
GET /api/v1/alerts?page=0&size=20&sort=createdAt,desc
```

Response includes metadata:
```json
{
  "content": [...],
  "page": {
    "number": 0,
    "size": 20,
    "totalElements": 150,
    "totalPages": 8
  }
}
```

---

## Database Guidelines

### Domain Entity (Domain Layer)

```kotlin
// domain/model/Alert.kt - Domain entity (sin dependencias de BD)
data class Alert(
    val id: UUID,
    val title: String,
    val description: String?,
    val category: AlertCategory,
    val location: Location,
    val address: String?,
    val severity: AlertSeverity,
    val status: AlertStatus,
    val createdBy: UUID,
    val neighborhoodId: UUID?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    init {
        require(title.isNotBlank()) { "Title must not be blank" }
        require(title.length <= 100) { "Title must not exceed 100 characters" }
    }
}
```

### Database Entity (Infrastructure Layer - Exposed)

```kotlin
// infrastructure/adapter/output/persistence/entity/AlertEntity.kt
object Alerts : Table("alerts") {
    val id = uuid("id").autoGenerate()
    val title = varchar("title", 100)
    val description = text("description").nullable()
    val category = varchar("category", 50)
    val severity = varchar("severity", 20)
    val status = varchar("status", 20)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = varchar("address", 255).nullable()
    val createdBy = uuid("created_by").references(Users.id)
    val neighborhoodId = uuid("neighborhood_id").references(Neighborhoods.id).nullable()
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}

// Entity class para DAO pattern (opcional)
class AlertEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AlertEntity>(Alerts)

    var title by Alerts.title
    var description by Alerts.description
    var category by Alerts.category
    var severity by Alerts.severity
    var status by Alerts.status
    var latitude by Alerts.latitude
    var longitude by Alerts.longitude
    var address by Alerts.address
    var createdBy by UserEntity referencedOn Alerts.createdBy
    var neighborhoodId by Alerts.neighborhoodId
    var createdAt by Alerts.createdAt
    var updatedAt by Alerts.updatedAt
}
```

### Repository Implementation (Infrastructure Layer)

```kotlin
// infrastructure/adapter/output/persistence/repository/PostgresAlertRepository.kt
class PostgresAlertRepository(
    private val database: Database,
    private val mapper: AlertEntityMapper
) : AlertRepository {

    override suspend fun findById(id: UUID): Alert? = dbQuery {
        Alerts.select { Alerts.id eq id }
            .singleOrNull()
            ?.let(mapper::toDomain)
    }

    override suspend fun save(alert: Alert): Alert = dbQuery {
        Alerts.insert {
            it[id] = alert.id
            it[title] = alert.title
            it[description] = alert.description
            it[category] = alert.category.name
            it[severity] = alert.severity.name
            it[status] = alert.status.name
            it[latitude] = alert.location.latitude
            it[longitude] = alert.location.longitude
            it[address] = alert.address
            it[createdBy] = alert.createdBy
            it[neighborhoodId] = alert.neighborhoodId
            it[createdAt] = alert.createdAt
            it[updatedAt] = alert.updatedAt
        }
        alert
    }

    override suspend fun findNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double
    ): List<Alert> = dbQuery {
        // Using PostGIS ST_DWithin for geospatial queries
        exec("""
            SELECT * FROM alerts
            WHERE ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint($longitude, $latitude), 4326)::geography,
                ${radiusKm * 1000}
            )
            AND status = 'ACTIVE'
        """) { rs ->
            buildList {
                while (rs.next()) {
                    add(mapper.fromResultSet(rs))
                }
            }
        } ?: emptyList()
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
```

### Migration Naming

Flyway/Liquibase migrations:
```
V1__initial_schema.sql
V2__add_neighborhoods_table.sql
V3__add_alert_categories.sql
V4__add_spatial_indexes.sql
```

### Indexes

Create indexes for:
- Foreign keys
- Frequently queried columns
- Geospatial columns (PostGIS spatial index)
- Composite indexes for common query patterns

```sql
-- Spatial index for location-based queries
CREATE INDEX idx_alerts_location ON alerts USING GIST (location);

-- Index for filtering by status and created date
CREATE INDEX idx_alerts_status_created ON alerts (status, created_at DESC);
```

### Naming Conventions

- **Tables**: plural, snake_case (e.g., `alerts`, `user_neighborhoods`)
- **Columns**: snake_case (e.g., `created_at`, `user_id`)
- **Indexes**: `idx_table_column(s)` (e.g., `idx_alerts_user_id`)
- **Foreign Keys**: `fk_table_referenced_table` (e.g., `fk_alerts_users`)

---

## Security Best Practices

### Authentication & Authorization (Ktor)

- **JWT Tokens**: Use for stateless authentication
- **Ktor Authentication**: Configure JWT-based auth
- **Password Hashing**: BCrypt with appropriate work factor (cost factor 12)
- **Token Expiration**: Access tokens (15 min), Refresh tokens (7 days)
- **Role-Based Access Control (RBAC)**: Implement custom authorization

### API Security Configuration

```kotlin
// infrastructure/config/SecurityConfig.kt
fun Application.configureSecurity() {
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("userId").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse("Invalid or expired token")
                )
            }
        }
    }
}

// Usage in routes
fun Route.alertRoutes() {
    route("/alerts") {
        // Public endpoint (no authentication)
        get {
            // List public alerts
        }

        // Protected endpoints
        authenticate("auth-jwt") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                // Create alert
            }

            delete("/{id}") {
                val userId = call.principal<JWTPrincipal>()
                    ?.payload?.getClaim("userId")?.asString()
                    ?: throw UnauthorizedException()
                // Delete alert (verify ownership)
            }
        }
    }
}
```

### Password Hashing

```kotlin
import org.mindrot.jbcrypt.BCrypt

class PasswordHasher {
    companion object {
        private const val BCRYPT_ROUNDS = 12

        fun hash(password: String): String =
            BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_ROUNDS))

        fun verify(password: String, hash: String): Boolean =
            BCrypt.checkpw(password, hash)
    }
}
```

### CORS Configuration

```kotlin
fun Application.configureCORS() {
    install(CORS) {
        allowMethod(HttpMethod.GET)
        allowMethod(HttpMethod.POST)
        allowMethod(HttpMethod.PUT)
        allowMethod(HttpMethod.DELETE)
        allowMethod(HttpMethod.PATCH)

        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        // Production: Specify exact origins
        allowHost("alertavecinal.com", schemes = listOf("https"))
        allowHost("localhost:3000") // Dev frontend

        allowCredentials = true
        maxAgeInSeconds = 3600
    }
}
```

### Input Validation with Konform

```kotlin
// Validation for CreateAlertRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.*

data class CreateAlertRequest(
    val title: String,
    val description: String?,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val severity: String
)

val validateCreateAlert = Validation<CreateAlertRequest> {
    CreateAlertRequest::title {
        minLength(10) hint "Title must be at least 10 characters"
        maxLength(100) hint "Title must not exceed 100 characters"
    }

    CreateAlertRequest::description ifPresent {
        minLength(20) hint "Description must be at least 20 characters"
        maxLength(2000) hint "Description must not exceed 2000 characters"
    }

    CreateAlertRequest::latitude {
        minimum(-90.0) hint "Latitude must be between -90 and 90"
        maximum(90.0) hint "Latitude must be between -90 and 90"
    }

    CreateAlertRequest::longitude {
        minimum(-180.0) hint "Longitude must be between -180 and 180"
        maximum(180.0) hint "Longitude must be between -180 and 180"
    }

    CreateAlertRequest::category {
        enum("SECURITY", "EMERGENCY", "TRAFFIC", "UTILITIES", "COMMUNITY", "LOST_FOUND", "OTHER")
    }

    CreateAlertRequest::severity {
        enum("CRITICAL", "HIGH", "MEDIUM", "LOW")
    }
}

// Usage in route
post("/alerts") {
    val request = call.receive<CreateAlertRequest>()
    val validationResult = validateCreateAlert(request)

    if (validationResult is Invalid) {
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(
                message = "Validation failed",
                errors = validationResult.errors.map { it.message }
            )
        )
        return@post
    }

    // Process valid request
}
```

### OWASP Top 10 Protection

#### 1. SQL Injection
- ✅ Use Exposed DSL with parameterized queries
- ✅ Never concatenate user input into SQL strings
- ✅ Use prepared statements for raw SQL

```kotlin
// SAFE: Using Exposed DSL
Alerts.select { Alerts.id eq alertId }

// SAFE: Parameterized raw SQL
exec("SELECT * FROM alerts WHERE id = ?", listOf(alertId))

// UNSAFE: Never do this
exec("SELECT * FROM alerts WHERE id = '$alertId'")
```

#### 2. Broken Authentication
- ✅ Implement JWT with short expiration
- ✅ Use BCrypt for password hashing (cost factor 12+)
- ✅ Implement refresh token rotation
- ✅ Lock accounts after failed login attempts
- ✅ Require strong passwords (min 8 chars, mixed case, numbers, symbols)

#### 3. Sensitive Data Exposure
- ✅ Never log passwords, tokens, or PII
- ✅ Use HTTPS in production (TLS 1.2+)
- ✅ Don't expose internal IDs (use UUIDs)
- ✅ Mask sensitive data in logs
- ✅ Encrypt sensitive data at rest

```kotlin
// Don't expose internal implementation
logger.info("User login attempt: ${user.email}") // ✅ OK
logger.debug("Password: ${password}") // ❌ NEVER

// Mask sensitive data
fun String.maskEmail() = this.replace(Regex("(?<=.{2}).(?=.*@)"), "*")
logger.info("User registered: ${"user@example.com".maskEmail()}") // us**@example.com
```

#### 4. XSS (Cross-Site Scripting)
- ✅ Sanitize user input before storage
- ✅ Use Content-Security-Policy headers
- ✅ Encode output properly (kotlinx.serialization handles this)

```kotlin
install(DefaultHeaders) {
    header("X-Content-Type-Options", "nosniff")
    header("X-Frame-Options", "DENY")
    header("X-XSS-Protection", "1; mode=block")
    header("Content-Security-Policy", "default-src 'self'")
}
```

#### 5. Broken Access Control
- ✅ Verify resource ownership before operations
- ✅ Implement RBAC (Role-Based Access Control)
- ✅ Use principle of least privilege

```kotlin
suspend fun verifyAlertOwnership(alertId: UUID, userId: UUID) {
    val alert = alertRepository.findById(alertId)
        ?: throw AlertNotFoundException(alertId)

    if (alert.createdBy != userId) {
        throw ForbiddenException("You don't have permission to modify this alert")
    }
}
```

#### 6. Security Misconfiguration
- ✅ Disable debug mode in production
- ✅ Remove default credentials
- ✅ Keep dependencies updated
- ✅ Use environment variables for secrets

```kotlin
// application.conf (HOCON)
ktor {
    deployment {
        port = 8080
        port = ${?PORT}
    }
    application {
        modules = [com.alertavecinal.ApplicationKt.module]
    }
    development = false  // Set to false in production
}

jwt {
    secret = ${JWT_SECRET}  // From environment variable
    issuer = ${JWT_ISSUER}
    audience = ${JWT_AUDIENCE}
    realm = "AlertaVecinal"
}
```

#### 7. CSRF (Cross-Site Request Forgery)
- ✅ Stateless JWT approach mitigates CSRF
- ✅ Verify JWT in Authorization header (not cookies)
- ✅ Use SameSite cookies if using cookie-based auth

### Rate Limiting (DDoS Protection)

```kotlin
// Simple in-memory rate limiter (use Redis in production)
class RateLimiter(
    private val maxRequests: Int = 100,
    private val windowSeconds: Long = 60
) {
    private val requests = ConcurrentHashMap<String, MutableList<Long>>()

    fun isAllowed(identifier: String): Boolean {
        val now = System.currentTimeMillis()
        val windowStart = now - (windowSeconds * 1000)

        val userRequests = requests.getOrPut(identifier) { mutableListOf() }
        userRequests.removeIf { it < windowStart }

        return if (userRequests.size < maxRequests) {
            userRequests.add(now)
            true
        } else {
            false
        }
    }
}

// Usage
val rateLimiter = RateLimiter(maxRequests = 100, windowSeconds = 60)

install(createApplicationPlugin(name = "RateLimiting") {
    onCall { call ->
        val ip = call.request.origin.remoteHost
        if (!rateLimiter.isAllowed(ip)) {
            call.respond(HttpStatusCode.TooManyRequests, "Rate limit exceeded")
        }
    }
})
```

---

## Common Commands

### Gradle (Ktor)

```bash
# Build project
./gradlew build

# Clean build
./gradlew clean build

# Run application
./gradlew run

# Run with auto-reload (development mode)
./gradlew -t installDist
./build/install/AlertaVecinalBackend/bin/AlertaVecinalBackend

# Run tests
./gradlew test

# Run specific test class
./gradlew test --tests "AlertServiceTest"

# Run tests with coverage (Kover)
./gradlew koverHtmlReport
# Report: build/reports/kover/html/index.html

# Generate JAR (fat JAR with dependencies)
./gradlew shadowJar
# Output: build/libs/AlertaVecinalBackend-all.jar

# Run JAR
java -jar build/libs/AlertaVecinalBackend-all.jar

# Check dependencies
./gradlew dependencies

# Update Gradle wrapper
./gradlew wrapper --gradle-version=8.5

# Format code (ktlint)
./gradlew ktlintFormat

# Check code style
./gradlew ktlintCheck
```

### Docker

```bash
# Build Docker image
docker build -t alerta-vecinal-backend:latest -f docker/Dockerfile .

# Run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down

# Rebuild and restart
docker-compose up -d --build
```

### Database

```bash
# Connect to local PostgreSQL
docker exec -it alertavecinal_postgres psql -U postgres -d alertavecinal

# Run migration manually (Flyway)
./gradlew flywayMigrate

# View migration status
./gradlew flywayInfo

# Repair migration checksums
./gradlew flywayRepair
```

### Git

```bash
# Create feature branch
git checkout -b feature/new-feature

# Commit with conventional commits
git commit -m "feat(alerts): add location-based filtering"

# Push to remote
git push -u origin feature/new-feature

# Pull latest changes
git pull origin main

# Rebase on main
git fetch origin
git rebase origin/main
```

---

## AI Assistant Guidelines

### When Working on This Codebase

#### 1. **Understand Before Implementing**
- Always read existing code before making changes
- Check for similar implementations or patterns
- Review related tests to understand expected behavior
- Look for configuration in `application.conf` (HOCON format)

#### 2. **Follow Established Patterns**
- Match existing code style and structure
- Use the same libraries and frameworks already in the project
- Follow Hexagonal Architecture: REST Adapter → Use Case → Domain → Repository Port → DB Adapter
- Respect layer boundaries: Domain never depends on Infrastructure
- Use Koin for dependency injection (no manual instantiation)
- Reuse existing utilities and helper classes

#### 3. **Security First**
- Never commit secrets, API keys, or credentials
- Validate all user inputs
- Use parameterized queries (JPA handles this)
- Be cautious with geolocation data (privacy concerns)
- Follow OWASP guidelines

#### 4. **Testing is Mandatory**
- Write tests for ALL new features
- Update tests when modifying existing code
- Ensure tests pass before committing
- Aim for high coverage (80%+ minimum)

#### 5. **Documentation**
- Update API documentation (OpenAPI spec if available)
- Add KDoc for public APIs and domain logic
- Update CLAUDE.md if architecture changes
- Update README.md for setup changes
- Document business rules in FEATURES.md

#### 6. **File Location Reference**
When referencing code, use the format: `file_path:line_number`

Example: "The alert creation logic is implemented in `src/main/kotlin/com/alertavecinal/application/service/AlertService.kt:45`"

#### 7. **Code Review Checklist**
Before marking work complete, verify:
- [ ] Code compiles and runs
- [ ] All tests pass
- [ ] No new compiler warnings
- [ ] Security best practices followed
- [ ] Error handling implemented
- [ ] Logging added for important operations
- [ ] API documentation updated
- [ ] Migration scripts created (if database changes)

#### 8. **Common Pitfalls to Avoid**
- ❌ Don't expose internal IDs in public APIs (use UUIDs)
- ❌ Don't return domain entities from routes (use DTOs/Response objects)
- ❌ Don't put business logic in routes (keep routes thin, logic in use cases)
- ❌ Don't let Domain layer depend on Infrastructure layer
- ❌ Don't log sensitive information (passwords, tokens, PII)
- ❌ Don't use `SELECT *` in queries
- ❌ Don't ignore transaction boundaries
- ❌ Don't forget to handle edge cases (null, empty, invalid data)
- ❌ Don't skip input validation
- ❌ Don't use `!!` (non-null assertion) - handle nulls explicitly
- ❌ Don't bypass Koin DI by manually instantiating dependencies

#### 9. **Performance Considerations**
- Use Exposed's lazy loading for relationships when needed
- Add database indexes for frequently queried fields (especially foreign keys)
- Use pagination for large result sets (limit + offset)
- Cache frequently accessed, rarely changing data
- Optimize spatial queries with proper PostGIS indexes (GIST)
- Use connection pooling (HikariCP already configured)
- Leverage Kotlin coroutines for async operations
- Avoid N+1 query problems (use eager loading when appropriate)

#### 10. **Logging Best Practices**
```kotlin
logger.debug("Processing alert creation request for user: {}", userId)
logger.info("Alert created successfully: {}", alertId)
logger.warn("Alert creation rate limit approaching for user: {}", userId)
logger.error("Failed to create alert", exception)
```

Levels:
- **DEBUG**: Detailed diagnostic information
- **INFO**: Significant events (creation, updates, deletions)
- **WARN**: Potential issues, rate limits, deprecations
- **ERROR**: Errors requiring attention

#### 11. **Branch and Commit Strategy**
- Work on feature branches, not main
- Use Claude branches (`claude/claude-md-*`) for AI-assisted work
- Push to remote regularly
- Create descriptive commit messages
- Reference issue numbers when applicable

#### 12. **When Stuck**
- Check Ktor documentation (https://ktor.io/docs)
- Review Kotlin best practices and coroutines documentation
- Check Exposed documentation for database queries
- Look at Koin documentation for DI issues
- Look at similar open-source projects using Ktor + Hexagonal Architecture
- Search for error messages
- Ask for user clarification if requirements are ambiguous

---

## Project Roadmap

### Phase 1: Foundation (Current)
- [x] Repository initialization
- [x] License and basic docs
- [x] CLAUDE.md technical guide created
- [x] FEATURES.md business domain documentation created
- [x] Architecture decision: Hexagonal Architecture
- [x] Technology stack defined: Ktor + Koin + Exposed + PostgreSQL
- [ ] Gradle project setup with build.gradle.kts
- [ ] Ktor application setup
- [ ] Koin dependency injection configuration
- [ ] Database configuration (PostgreSQL + PostGIS)
- [ ] Basic directory structure implementation
- [ ] First domain entities and use cases

### Phase 2: Core Features
- [ ] User authentication and authorization
- [ ] User profile management
- [ ] Neighborhood management
- [ ] Alert creation and management
- [ ] Geolocation-based alert filtering
- [ ] Real-time notifications

### Phase 3: Advanced Features
- [ ] Alert categories and tagging
- [ ] Image/media attachments
- [ ] Alert comments and interactions
- [ ] Admin dashboard
- [ ] Analytics and reporting
- [ ] Push notifications

### Phase 4: Production Readiness
- [ ] Performance optimization
- [ ] Comprehensive testing
- [ ] CI/CD pipeline
- [ ] Monitoring and alerting
- [ ] Documentation completion
- [ ] Production deployment

---

## Resources

### Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [PostGIS Documentation](https://postgis.net/documentation/)
- [Kotlin Language Guide](https://kotlinlang.org/docs/home.html)

### Tools
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - Recommended IDE
- [Postman](https://www.postman.com/) - API testing
- [DBeaver](https://dbeaver.io/) - Database management
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) - Containerization

### Community
- Project Repository: [GitHub](https://github.com/pedrorau/AlertaVecinalBackend)
- Issue Tracker: GitHub Issues
- License: Apache 2.0

---

## Changelog

### 2025-11-17
- **Major Update**: Switched from Spring Boot to Ktor framework
- Added Koin for dependency injection (replacing Spring DI)
- Implemented Hexagonal Architecture (Ports & Adapters)
- Updated all code examples for Ktor
- Added Exposed framework for database access
- Created separate FEATURES.md for business domain documentation
- Updated security section with Ktor Authentication and JWT
- Added comprehensive input validation with Konform
- Updated database examples with Exposed DSL
- Added CORS and rate limiting configurations
- Updated Common Commands for Ktor/Gradle

### 2025-11-16
- Initial CLAUDE.md creation
- Project structure documentation
- Development workflow guidelines
- AI assistant best practices

---

**Note**: This document should be updated whenever significant architectural decisions are made or development workflows change. All AI assistants and human developers should refer to this guide when contributing to the project.
