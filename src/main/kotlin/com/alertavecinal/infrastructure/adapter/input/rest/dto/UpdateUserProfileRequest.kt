package com.alertavecinal.infrastructure.adapter.input.rest.dto

import kotlinx.serialization.Serializable

/**
 * Request DTO for updating user profile.
 * Received from clients via REST API.
 */
@Serializable
data class UpdateUserProfileRequest(
    val displayName: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val bio: String? = null
)
