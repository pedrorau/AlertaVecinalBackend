package com.alertavecinal.application.service

import com.alertavecinal.application.port.output.UserRepository
import com.alertavecinal.domain.exception.UserNotFoundException
import com.alertavecinal.domain.model.User
import com.alertavecinal.domain.model.UserProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserServiceTest {

    private val userRepository: UserRepository = mockk()
    private val userService = UserService(userRepository)

    @Test
    fun `should get user profile by id`() = runBlocking {
        val userId = UUID.randomUUID()
        val user = User(
            id = userId,
            email = "test@example.com",
            profile = UserProfile.empty(),
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        coEvery { userRepository.findById(userId) } returns user

        val result = userService.execute(userId)

        assertNotNull(result)
        assertEquals(userId, result.id)
        coVerify { userRepository.findById(userId) }
    }

    @Test
    fun `should throw UserNotFoundException when user not found`() = runBlocking {
        val userId = UUID.randomUUID()

        coEvery { userRepository.findById(userId) } returns null

        assertThrows<UserNotFoundException> {
            userService.execute(userId)
        }
    }

    @Test
    fun `should update user profile`() = runBlocking {
        val userId = UUID.randomUUID()
        val existingUser = User(
            id = userId,
            email = "test@example.com",
            profile = UserProfile.empty(),
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        val newProfile = UserProfile(
            displayName = "Updated Name",
            photoUrl = null,
            phoneNumber = null,
            address = null,
            bio = null
        )

        coEvery { userRepository.findById(userId) } returns existingUser
        coEvery { userRepository.save(any()) } answers { firstArg() }

        val result = userService.execute(userId, newProfile)

        assertNotNull(result)
        assertEquals("Updated Name", result.profile.displayName)
        coVerify { userRepository.findById(userId) }
        coVerify { userRepository.save(any()) }
    }
}
