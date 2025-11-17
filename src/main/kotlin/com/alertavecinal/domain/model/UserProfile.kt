package com.alertavecinal.domain.model

/**
 * Value object representing user profile information.
 * Immutable by design following DDD principles.
 */
data class UserProfile(
    val displayName: String?,
    val photoUrl: String?,
    val phoneNumber: String?,
    val address: String?,
    val bio: String?
) {
    init {
        displayName?.let {
            require(it.isNotBlank()) { "Display name must not be blank if provided" }
            require(it.length <= 100) { "Display name must not exceed 100 characters" }
        }

        bio?.let {
            require(it.length <= 500) { "Bio must not exceed 500 characters" }
        }

        phoneNumber?.let {
            require(it.matches(Regex("^\\+?[1-9]\\d{1,14}$"))) {
                "Phone number must be valid (E.164 format)"
            }
        }
    }

    companion object {
        /**
         * Creates an empty profile
         */
        fun empty() = UserProfile(
            displayName = null,
            photoUrl = null,
            phoneNumber = null,
            address = null,
            bio = null
        )
    }
}
