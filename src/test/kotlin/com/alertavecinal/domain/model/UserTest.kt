package com.alertavecinal.domain.model

import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserTest {

    @Test
    fun `should create valid user`() {
        val user = User(
            id = UUID.randomUUID(),
            email = "test@example.com",
            profile = UserProfile.empty(),
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        assertNotNull(user)
        assertEquals("test@example.com", user.email)
    }

    @Test
    fun `should reject blank email`() {
        assertThrows<IllegalArgumentException> {
            User(
                id = UUID.randomUUID(),
                email = "",
                profile = UserProfile.empty(),
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            )
        }
    }

    @Test
    fun `should reject invalid email`() {
        assertThrows<IllegalArgumentException> {
            User(
                id = UUID.randomUUID(),
                email = "invalid-email",
                profile = UserProfile.empty(),
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            )
        }
    }

    @Test
    fun `should update profile`() {
        val user = User(
            id = UUID.randomUUID(),
            email = "test@example.com",
            profile = UserProfile.empty(),
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        val newProfile = UserProfile(
            displayName = "Test User",
            photoUrl = null,
            phoneNumber = null,
            address = null,
            bio = null
        )

        val updatedUser = user.updateProfile(newProfile, Clock.System.now())

        assertEquals("Test User", updatedUser.profile.displayName)
    }
}
