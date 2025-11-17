package com.alertavecinal

import com.alertavecinal.infrastructure.config.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/**
 * Main application entry point.
 * Configures and starts the Ktor server.
 */
fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toIntOrNull() ?: 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

/**
 * Application module configuration.
 * Called by Ktor to set up the application.
 */
fun Application.module() {
    // Dependency Injection
    configureKoin()

    // Database
    configureDatabases()

    // Serialization
    configureSerialization()

    // Security & Auth
    configureSecurity()
    configureCORS()

    // Exception Handling
    configureStatusPages()

    // Routes
    configureRouting()
}
