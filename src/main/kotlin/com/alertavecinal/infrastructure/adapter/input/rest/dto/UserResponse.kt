package com.alertavecinal.infrastructure.adapter.input.rest.dto

import kotlinx.serialization.Serializable

/**
 * Response DTO for user profile information.
 * Sent to clients via REST API.
 */
@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val phoneNumber: String?,
    val address: String?,
    val bio: String?,
    val createdAt: String,
    val updatedAt: String
)

/**
 * Response DTO for user profile (public view - limited info)
 */
@Serializable
data class UserPublicProfileResponse(
    val id: String,
    val displayName: String?,
    val photoUrl: String?,
    val bio: String?
)
