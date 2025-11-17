package com.alertavecinal.domain.model

import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Domain entity representing a user in the system.
 * This entity contains only business logic and no infrastructure concerns.
 */
data class User(
    val id: UUID,
    val email: String,
    val profile: UserProfile,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    init {
        require(email.isNotBlank()) { "Email must not be blank" }
        require(email.contains("@")) { "Email must be valid" }
    }

    /**
     * Creates an updated copy of the user with a new profile
     */
    fun updateProfile(newProfile: UserProfile, updatedAt: Instant): User {
        return copy(profile = newProfile, updatedAt = updatedAt)
    }
}
