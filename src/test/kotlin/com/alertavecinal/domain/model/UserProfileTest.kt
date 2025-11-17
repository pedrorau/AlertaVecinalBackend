package com.alertavecinal.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserProfileTest {

    @Test
    fun `should create empty profile`() {
        val profile = UserProfile.empty()

        assertNull(profile.displayName)
        assertNull(profile.photoUrl)
        assertNull(profile.phoneNumber)
        assertNull(profile.address)
        assertNull(profile.bio)
    }

    @Test
    fun `should create valid profile with all fields`() {
        val profile = UserProfile(
            displayName = "John Doe",
            photoUrl = "https://example.com/photo.jpg",
            phoneNumber = "+1234567890",
            address = "123 Main St",
            bio = "Software developer"
        )

        assertEquals("John Doe", profile.displayName)
        assertEquals("https://example.com/photo.jpg", profile.photoUrl)
    }

    @Test
    fun `should reject blank display name`() {
        assertThrows<IllegalArgumentException> {
            UserProfile(
                displayName = "",
                photoUrl = null,
                phoneNumber = null,
                address = null,
                bio = null
            )
        }
    }

    @Test
    fun `should reject display name over 100 characters`() {
        assertThrows<IllegalArgumentException> {
            UserProfile(
                displayName = "a".repeat(101),
                photoUrl = null,
                phoneNumber = null,
                address = null,
                bio = null
            )
        }
    }

    @Test
    fun `should reject bio over 500 characters`() {
        assertThrows<IllegalArgumentException> {
            UserProfile(
                displayName = "John Doe",
                photoUrl = null,
                phoneNumber = null,
                address = null,
                bio = "a".repeat(501)
            )
        }
    }

    @Test
    fun `should reject invalid phone number`() {
        assertThrows<IllegalArgumentException> {
            UserProfile(
                displayName = "John Doe",
                photoUrl = null,
                phoneNumber = "invalid",
                address = null,
                bio = null
            )
        }
    }

    @Test
    fun `should accept valid E164 phone number`() {
        val profile = UserProfile(
            displayName = "John Doe",
            photoUrl = null,
            phoneNumber = "+5491123456789",
            address = null,
            bio = null
        )

        assertEquals("+5491123456789", profile.phoneNumber)
    }
}
