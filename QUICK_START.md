# Quick Start Guide

Get the AlertaVecinalBackend running in 15 minutes.

## Prerequisites

- ✅ Java 17 or higher
- ✅ Supabase account (free tier is fine)
- ✅ Git

---

## 1. Clone and Setup

```bash
# Clone the repository (if not already done)
git clone <your-repo-url>
cd AlertaVecinalBackend

# Create environment file
cp .env.example .env
```

---

## 2. Configure Supabase

Follow **SETUP_SUPABASE.md** to:
1. Create a Supabase project (~2 min)
2. Get API credentials (~2 min)
3. Update `.env` file with your credentials (~2 min)
4. Create database tables (~2 min)

**Total time**: ~10 minutes

---

## 3. Run the Backend

```bash
# Build and run
./gradlew run

# Or use Gradle from your system
gradle run
```

The server will start on `http://localhost:8080`

---

## 4. Test the API

### Health Check (no auth required)
```bash
curl http://localhost:8080/health
```

Expected response:
```json
{
  "status": "UP",
  "service": "AlertaVecinalBackend",
  "version": "0.0.1"
}
```

### Get User Profile (requires auth)

First, create a test user in Supabase dashboard, then:

```bash
# Get JWT token from Supabase (sign in via mobile app or Supabase SDK)
export TOKEN="your-jwt-token-here"

# Get your profile
curl -H "Authorization: Bearer $TOKEN" \
     http://localhost:8080/api/v1/users/me
```

### Update User Profile
```bash
curl -X PATCH \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"displayName": "John Doe", "bio": "Software developer"}' \
     http://localhost:8080/api/v1/users/me
```

---

## 5. Run Tests

```bash
# Run all tests
./gradlew test

# View test report
open build/reports/tests/test/index.html
```

---

## Available Endpoints

### Public
- `GET /health` - Health check

### Protected (require JWT)
- `GET /api/v1/users/me` - Get current user profile
- `PATCH /api/v1/users/me` - Update current user profile
- `GET /api/v1/users/{id}` - Get public profile of another user

---

## Project Structure

```
src/main/kotlin/com/alertavecinal/
├── domain/                     # Business logic (pure Kotlin)
│   ├── model/                  # Domain entities
│   └── exception/              # Domain exceptions
├── application/                # Use cases
│   ├── port/input/             # Use case interfaces
│   ├── port/output/            # Repository interfaces
│   └── service/                # Use case implementations
└── infrastructure/             # Technical details
    ├── adapter/
    │   ├── input/rest/         # REST API
    │   └── output/persistence/ # Database
    └── config/                 # Configuration
```

---

## Development Workflow

1. **Make changes** to the code
2. **Run tests**: `./gradlew test`
3. **Run app**: `./gradlew run`
4. **Test manually** with curl or Postman
5. **Commit** with conventional commits:
   ```bash
   git add .
   git commit -m "feat: add user profile endpoint"
   git push
   ```

---

## Common Issues

### Port already in use
```bash
# Change port in .env
PORT=8081
```

### Database connection error
- Verify `.env` has correct Supabase credentials
- Check Supabase project is running
- Ensure database URL uses JDBC format: `jdbc:postgresql://...`

### JWT validation error
- Verify `JWT_SECRET` matches Supabase JWT secret
- Check token is fresh (not expired)
- Ensure `Authorization: Bearer <token>` header is set

---

## Next Steps

- 📖 Read **CLAUDE.md** for architecture details
- 📖 Read **SETUP_SUPABASE.md** for Supabase configuration
- 🚀 Implement alert management features
- 📱 Integrate with your mobile app

---

## Useful Commands

```bash
# Build project
./gradlew build

# Clean build
./gradlew clean build

# Run tests
./gradlew test

# Run with auto-reload (development mode)
./gradlew -t run

# Check dependencies
./gradlew dependencies

# Format code (if ktlint is configured)
./gradlew ktlintFormat
```

---

**Happy coding!** 🎉
