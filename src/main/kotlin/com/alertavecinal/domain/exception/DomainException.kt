package com.alertavecinal.domain.exception

/**
 * Base class for all domain exceptions.
 * Domain exceptions represent business rule violations.
 */
sealed class DomainException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)

/**
 * Thrown when a user is not found
 */
class UserNotFoundException(userId: String) : DomainException(
    message = "User not found: $userId"
)

/**
 * Thrown when attempting to create a user that already exists
 */
class UserAlreadyExistsException(email: String) : DomainException(
    message = "User with email $email already exists"
)

/**
 * Thrown when user data validation fails
 */
class InvalidUserDataException(
    field: String,
    reason: String
) : DomainException(
    message = "Invalid user data - $field: $reason"
)

/**
 * Thrown when a user is not authorized to perform an action
 */
class UnauthorizedException(
    action: String = "perform this action"
) : DomainException(
    message = "Unauthorized to $action"
)
