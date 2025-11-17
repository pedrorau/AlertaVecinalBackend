# Supabase Setup Guide

This guide will help you set up Supabase for the AlertaVecinalBackend project.

## Prerequisites

- A Supabase account (sign up at https://supabase.com)
- ~10 minutes of your time

---

## Step 1: Create a Supabase Project

1. Go to https://supabase.com/dashboard
2. Click **"New Project"**
3. Fill in the project details:
   - **Name**: `AlertaVecinal` (or any name you prefer)
   - **Database Password**: Choose a strong password (save it!)
   - **Region**: Choose closest to your users
   - **Pricing Plan**: Free (for POC)
4. Click **"Create new project"**
5. Wait ~2 minutes for the project to be provisioned

---

## Step 2: Get API Credentials

### 2.1 Get Project URL and Keys

1. In your Supabase dashboard, go to **Settings** → **API**
2. You'll see the following values:

   - **Project URL**: `https://xxxxx.supabase.co`
   - **anon public**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
   - **service_role**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

3. Copy these values - you'll need them in Step 4

### 2.2 Get JWT Secret

1. Still in **Settings** → **API**, scroll down to **JWT Settings**
2. Copy the **JWT Secret** value
3. This is the same secret used to sign all JWT tokens

---

## Step 3: Get Database Connection Details

1. Go to **Settings** → **Database**
2. Scroll to **Connection String** section
3. Select the **"URI"** tab
4. Copy the connection string (it looks like):
   ```
   postgresql://postgres:[YOUR-PASSWORD]@db.xxxxx.supabase.co:5432/postgres
   ```
5. **IMPORTANT**: Replace `[YOUR-PASSWORD]` with the database password you created in Step 1

---

## Step 4: Configure Your Backend

1. In your backend project root, create a `.env` file:
   ```bash
   cp .env.example .env
   ```

2. Open `.env` and fill in the values you copied:

   ```env
   # From Step 2.1
   SUPABASE_URL=https://xxxxx.supabase.co
   SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   SUPABASE_SERVICE_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

   # From Step 2.2
   JWT_SECRET=your-jwt-secret-here
   JWT_ISSUER=https://xxxxx.supabase.co/auth/v1
   JWT_AUDIENCE=authenticated

   # From Step 3 (convert to JDBC format)
   DATABASE_URL=jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres
   DATABASE_USER=postgres
   DATABASE_PASSWORD=your-database-password-here
   ```

3. **Convert PostgreSQL URL to JDBC format**:
   - Original: `postgresql://postgres:password@db.xxx.supabase.co:5432/postgres`
   - JDBC: `jdbc:postgresql://db.xxx.supabase.co:5432/postgres`
   - Just add `jdbc:` at the beginning!

---

## Step 5: Enable Email Authentication (Optional)

If you want users to sign up with email/password:

1. Go to **Authentication** → **Providers**
2. Enable **Email**
3. Configure email settings:
   - **Enable email confirmations**: OFF (for development)
   - **Enable email confirmations**: ON (for production)

---

## Step 6: Create Database Table for Users

Supabase creates an `auth.users` table automatically, but we need our own `public.users` table for additional profile data.

1. Go to **SQL Editor** in Supabase dashboard
2. Click **"New Query"**
3. Paste this SQL:

```sql
-- Create users table for extended profile data
CREATE TABLE IF NOT EXISTS public.users (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(100),
    photo_url VARCHAR(500),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    bio TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Create index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON public.users(email);

-- Create updated_at trigger
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON public.users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Enable Row Level Security (RLS)
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;

-- Policy: Users can read their own profile
CREATE POLICY "Users can read own profile"
    ON public.users
    FOR SELECT
    USING (auth.uid() = id);

-- Policy: Users can update their own profile
CREATE POLICY "Users can update own profile"
    ON public.users
    FOR UPDATE
    USING (auth.uid() = id);

-- Policy: Users can insert their own profile (after signup)
CREATE POLICY "Users can insert own profile"
    ON public.users
    FOR INSERT
    WITH CHECK (auth.uid() = id);

-- Policy: Anyone can read public profiles (for viewing other users)
CREATE POLICY "Anyone can read public user data"
    ON public.users
    FOR SELECT
    USING (true);
```

4. Click **"Run"** to execute the SQL

---

## Step 7: Test Your Setup

1. Make sure your `.env` file is correctly configured
2. Start your backend:
   ```bash
   ./gradlew run
   ```

3. Test the health endpoint:
   ```bash
   curl http://localhost:8080/health
   ```

   You should get:
   ```json
   {
     "status": "UP",
     "service": "AlertaVecinalBackend",
     "version": "0.0.1"
   }
   ```

---

## Step 8: Test Authentication Flow (Mobile App)

In your mobile app, use the Supabase SDK:

### Android/Kotlin
```kotlin
val supabase = createSupabaseClient(
    supabaseUrl = "https://xxxxx.supabase.co",
    supabaseKey = "your-anon-key"
) {
    install(Auth)
}

// Sign up
val user = supabase.auth.signUpWith(Email) {
    email = "user@example.com"
    password = "securePassword123"
}

// Get JWT token
val session = supabase.auth.currentSessionOrNull()
val jwtToken = session?.accessToken

// Use token in API calls to your backend
val response = client.get("http://localhost:8080/api/v1/users/me") {
    header("Authorization", "Bearer $jwtToken")
}
```

### React Native/JavaScript
```javascript
import { createClient } from '@supabase/supabase-js'

const supabase = createClient(
  'https://xxxxx.supabase.co',
  'your-anon-key'
)

// Sign up
const { data, error } = await supabase.auth.signUp({
  email: 'user@example.com',
  password: 'securePassword123'
})

// Get JWT token
const { data: { session } } = await supabase.auth.getSession()
const jwtToken = session.access_token

// Use token in API calls
const response = await fetch('http://localhost:8080/api/v1/users/me', {
  headers: {
    'Authorization': `Bearer ${jwtToken}`
  }
})
```

---

## Step 9: Create Test User (Optional)

To test immediately without mobile app:

1. Go to **Authentication** → **Users** in Supabase dashboard
2. Click **"Add user"** → **"Create new user"**
3. Enter email and password
4. Click **"Create user"**
5. User will be created in `auth.users`

Then create the profile in SQL Editor:
```sql
INSERT INTO public.users (id, email, display_name)
VALUES (
    'paste-user-id-from-auth-users-here',
    'user@example.com',
    'Test User'
);
```

---

## Troubleshooting

### Error: "Could not connect to database"
- Check that `DATABASE_URL` is correct
- Verify database password in `.env`
- Make sure you're using JDBC format: `jdbc:postgresql://...`

### Error: "Invalid JWT token"
- Verify `JWT_SECRET` matches Supabase JWT secret
- Check `JWT_ISSUER` is correct (should end with `/auth/v1`)
- Ensure token is being sent in `Authorization: Bearer <token>` header

### Error: "Row Level Security policy violation"
- Make sure RLS policies are created (Step 6)
- Verify user ID in JWT matches user ID in database

---

## Security Notes

⚠️ **IMPORTANT**:

- **NEVER** commit `.env` to Git (it's in `.gitignore`)
- **NEVER** expose `SUPABASE_SERVICE_KEY` to clients (use only server-side)
- Use `SUPABASE_ANON_KEY` for client apps (it's safe and has RLS restrictions)
- Enable RLS on all tables in production
- Use environment variables in production deployments

---

## Next Steps

1. ✅ Backend is ready!
2. Configure Supabase Auth in your mobile app
3. Test login/signup flow
4. Test API endpoints with JWT tokens
5. Implement user profile features

---

## Useful Links

- [Supabase Documentation](https://supabase.com/docs)
- [Supabase Auth with Kotlin](https://github.com/supabase-community/supabase-kt)
- [Supabase JS Client](https://supabase.com/docs/reference/javascript/introduction)
- [Row Level Security Guide](https://supabase.com/docs/guides/auth/row-level-security)

---

**Need help?** Check the Supabase Discord or GitHub issues.
