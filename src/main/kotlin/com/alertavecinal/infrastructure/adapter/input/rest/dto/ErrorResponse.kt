package com.alertavecinal.infrastructure.adapter.input.rest.dto

import kotlinx.serialization.Serializable

/**
 * Standard error response format for API errors
 */
@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String? = null,
    val timestamp: String = kotlinx.datetime.Clock.System.now().toString()
)
