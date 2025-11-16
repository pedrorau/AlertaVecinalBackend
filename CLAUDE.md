# CLAUDE.md - AI Assistant Guide for AlertaVecinalBackend

**Last Updated**: 2025-11-16
**Project**: AlertaVecinalBackend
**License**: Apache 2.0
**Primary Language**: Java/Kotlin (JVM-based)

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
2. **Framework**: Spring Boot 3.x
3. **Language**: Kotlin (preferred) or Java 17+
4. **Database**: PostgreSQL with PostGIS extension (for geospatial features)
5. **Project Structure**: Standard Maven directory layout

---

## Technology Stack

### Expected Core Technologies

#### Backend Framework
- **Spring Boot 3.x** - Main application framework
- **Spring Web** - REST API controllers
- **Spring Data JPA** - Database ORM
- **Spring Security** - Authentication and authorization
- **Spring Validation** - Request validation

#### Database
- **PostgreSQL 15+** - Primary relational database
- **PostGIS** - Geospatial extension for location-based queries
- **Flyway** or **Liquibase** - Database migrations

#### Build & Dependency Management
- **Gradle 8.x** - Build automation
- **Kotlin DSL** - Build script language
- **Gradle Version Catalog** - Centralized dependency management

#### Testing
- **JUnit 5** - Unit testing framework
- **MockK** - Mocking library (for Kotlin)
- **Testcontainers** - Integration testing with Docker
- **REST Assured** - API testing
- **Jacoco** - Code coverage

#### Additional Libraries
- **Jackson** - JSON serialization
- **Hibernate Spatial** - Geospatial entity support
- **Springdoc OpenAPI** - API documentation
- **Logback** - Logging framework
- **MapStruct** - DTO mapping

#### DevOps & Tools
- **Docker** - Containerization
- **Docker Compose** - Local development environment
- **GitHub Actions** - CI/CD pipeline
- **Prometheus** & **Micrometer** - Metrics and monitoring

---

## Directory Structure

### Expected Layout

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
│   │   ├── kotlin/             # or java/
│   │   │   └── com/alertavecinal/backend/
│   │   │       ├── AlertaVecinalApplication.kt
│   │   │       ├── config/     # Configuration classes
│   │   │       ├── controller/ # REST controllers
│   │   │       ├── service/    # Business logic
│   │   │       ├── repository/ # Data access layer
│   │   │       ├── model/      # Domain entities
│   │   │       ├── dto/        # Data transfer objects
│   │   │       ├── mapper/     # Entity-DTO mappers
│   │   │       ├── security/   # Security configuration
│   │   │       ├── exception/  # Custom exceptions
│   │   │       └── util/       # Utility classes
│   │   └── resources/
│   │       ├── application.yml         # Main config
│   │       ├── application-dev.yml     # Dev profile
│   │       ├── application-prod.yml    # Prod profile
│   │       └── db/migration/           # Flyway migrations
│   │           └── V1__initial_schema.sql
│   └── test/
│       ├── kotlin/             # or java/
│       │   └── com/alertavecinal/backend/
│       │       ├── integration/    # Integration tests
│       │       ├── unit/           # Unit tests
│       │       └── util/           # Test utilities
│       └── resources/
│           └── application-test.yml
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── docs/
│   ├── api/                    # API documentation
│   ├── architecture/           # Architecture diagrams
│   └── setup/                  # Setup guides
├── scripts/
│   ├── setup-local-db.sh
│   └── run-tests.sh
├── .gitignore
├── .editorconfig               # Code style config
├── build.gradle.kts            # Gradle build file
├── settings.gradle.kts         # Gradle settings
├── gradlew                     # Gradle wrapper (Unix)
├── gradlew.bat                 # Gradle wrapper (Windows)
├── CLAUDE.md                   # This file
├── README.md                   # User documentation
└── LICENSE                     # Apache 2.0 License
```

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
./gradlew bootRun

# 6. Access the API
curl http://localhost:8080/api/health
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
- Use reverse domain: `com.alertavecinal.backend`
- Organize by feature or layer (prefer feature-based)

### Class Naming
- **Controllers**: `*Controller` (e.g., `AlertController`)
- **Services**: `*Service` (e.g., `AlertService`)
- **Repositories**: `*Repository` (e.g., `AlertRepository`)
- **Entities**: Domain nouns (e.g., `Alert`, `User`, `Neighborhood`)
- **DTOs**: `*Request`, `*Response`, `*DTO` (e.g., `CreateAlertRequest`)
- **Mappers**: `*Mapper` (e.g., `AlertMapper`)

### Code Style
- **Indentation**: 4 spaces (no tabs)
- **Line Length**: 120 characters max
- **Braces**: K&R style (opening brace on same line)
- **Imports**: No wildcards, organize automatically
- **Naming**: camelCase for variables/methods, PascalCase for classes

### Kotlin Specific
- Use `data class` for DTOs
- Prefer `val` over `var`
- Use nullable types explicitly (`?`)
- Leverage extension functions
- Use `when` instead of long `if-else` chains

### Java Specific
- Use Java 17+ features (records, sealed classes)
- Prefer `final` for variables when possible
- Use Optional for nullable returns
- Leverage Stream API for collections

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

### Entity Design

```kotlin
@Entity
@Table(name = "alerts")
data class Alert(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val description: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    val category: AlertCategory,

    @Column(nullable = false)
    val location: Point, // PostGIS geometry type

    @Column(length = 255)
    val address: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val severity: AlertSeverity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val createdBy: User,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now()
)
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

### Authentication & Authorization

- **JWT Tokens**: Use for stateless authentication
- **Spring Security**: Configure role-based access control (RBAC)
- **Password Hashing**: BCrypt with appropriate work factor
- **Token Expiration**: Access tokens (15 min), Refresh tokens (7 days)

### API Security

```kotlin
@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Use JWT, disable CSRF
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll()
                  .requestMatchers("/api/v1/alerts/**").authenticated()
                  .requestMatchers("/actuator/health").permitAll()
                  .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }
}
```

### Input Validation

Always validate:
- Request body fields (`@Valid`, `@NotNull`, `@Size`, etc.)
- Path parameters and query parameters
- File uploads (type, size)
- Geolocation coordinates (valid ranges)

### OWASP Top 10 Protection

- **SQL Injection**: Use JPA/Hibernate parameterized queries
- **XSS**: Sanitize user input, use Content-Security-Policy headers
- **CSRF**: Stateless JWT approach mitigates CSRF
- **Sensitive Data**: Never log passwords, tokens, or PII
- **Security Headers**: Use Helmet-like configurations

```yaml
# application.yml
spring:
  security:
    headers:
      content-security-policy: "default-src 'self'"
      x-frame-options: DENY
      x-content-type-options: nosniff
```

---

## Common Commands

### Gradle

```bash
# Build project
./gradlew build

# Clean build
./gradlew clean build

# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Generate JAR
./gradlew bootJar

# Check dependencies
./gradlew dependencies

# Update Gradle wrapper
./gradlew wrapper --gradle-version=8.5
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
- Look for configuration in `application.yml`

#### 2. **Follow Established Patterns**
- Match existing code style and structure
- Use the same libraries and frameworks already in the project
- Follow the layered architecture: Controller → Service → Repository
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
- Update OpenAPI/Swagger annotations
- Add KDoc/JavaDoc for public APIs
- Update CLAUDE.md if architecture changes
- Update README.md for setup changes

#### 6. **File Location Reference**
When referencing code, use the format: `file_path:line_number`

Example: "The alert validation is implemented in `src/main/kotlin/com/alertavecinal/backend/service/AlertService.kt:45`"

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
- ❌ Don't return entities directly from controllers (use DTOs)
- ❌ Don't log sensitive information
- ❌ Don't use `SELECT *` in queries
- ❌ Don't ignore transaction boundaries
- ❌ Don't forget to handle edge cases (null, empty, invalid data)
- ❌ Don't skip input validation

#### 9. **Performance Considerations**
- Use lazy loading for relationships (`FetchType.LAZY`)
- Add database indexes for frequently queried fields
- Use pagination for large result sets
- Cache frequently accessed, rarely changing data
- Optimize spatial queries with proper PostGIS indexes

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
- Check Spring Boot documentation
- Review Kotlin/Java best practices
- Look at similar open-source projects
- Search for error messages
- Ask for user clarification if requirements are ambiguous

---

## Project Roadmap

### Phase 1: Foundation (Current)
- [x] Repository initialization
- [x] License and basic docs
- [ ] Gradle project setup
- [ ] Spring Boot integration
- [ ] Database configuration
- [ ] Basic project structure

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

### 2025-11-16
- Initial CLAUDE.md creation
- Project structure documentation
- Development workflow guidelines
- AI assistant best practices

---

**Note**: This document should be updated whenever significant architectural decisions are made or development workflows change. All AI assistants and human developers should refer to this guide when contributing to the project.
