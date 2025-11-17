package com.alertavecinal.infrastructure.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

/**
 * Configures CORS for the application.
 * Allows requests from mobile apps and web clients.
 */
fun Application.configureCORS() {
    install(CORS) {
        // HTTP methods
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)

        // Headers
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader("apikey") // Supabase anon key header

        // Allow credentials (for cookies if needed)
        allowCredentials = true

        // Max age for preflight requests
        maxAgeInSeconds = 3600

        // Development: allow all hosts (CHANGE IN PRODUCTION!)
        anyHost()

        // Production: Uncomment and specify exact origins
        // allowHost("your-app.com", schemes = listOf("https"))
        // allowHost("localhost:3000") // React/mobile dev server
        // allowHost("localhost:8081") // React Native dev
    }
}
