package com.alertavecinal.infrastructure.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

/**
 * Configures JWT-based authentication using Supabase tokens.
 * Supabase generates standard JWT tokens that we validate here.
 */
fun Application.configureSecurity() {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.propertyOrNull("jwt.issuer")?.getString()
        ?: "https://your-project.supabase.co/auth/v1"
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")?.getString()
        ?: "authenticated"

    install(Authentication) {
        jwt("supabase-jwt") {
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .withAudience(jwtAudience)
                    .build()
            )

            validate { credential ->
                // Extract user ID from Supabase JWT token
                val userId = credential.payload.getClaim("sub").asString()
                val email = credential.payload.getClaim("email").asString()

                if (!userId.isNullOrBlank() && !email.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf(
                        "error" to "Invalid or expired token",
                        "message" to "Please authenticate with a valid Supabase token"
                    )
                )
            }
        }
    }
}

/**
 * Extension function to extract user ID from JWT principal
 */
fun JWTPrincipal.getUserId(): String {
    return payload.getClaim("sub").asString()
        ?: throw IllegalStateException("User ID not found in token")
}

/**
 * Extension function to extract email from JWT principal
 */
fun JWTPrincipal.getEmail(): String {
    return payload.getClaim("email").asString()
        ?: throw IllegalStateException("Email not found in token")
}
