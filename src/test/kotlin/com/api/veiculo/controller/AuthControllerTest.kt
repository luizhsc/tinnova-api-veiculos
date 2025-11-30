package com.api.veiculo.controller

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.service.AuthService
import com.mercadolivro.controller.response.AuthResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthControllerTest {

    private lateinit var authService: AuthService
    private lateinit var authController: AuthController

    @BeforeEach
    fun setup() {
        authService = mockk()
        authController = AuthController(authService)
    }

    @Test
    fun `deve registrar um usuário com sucesso`() {
        val request = AuthRequest(
            username = "user",
            password = "12345"
        )

        every { authService.register(request) } returns Unit

        val result = authController.register(request)

        verify(exactly = 1) { authService.register(request) }
        assertEquals(Unit, result)
    }

    @Test
    fun `deve realizar login e retornar AuthResponse`() {
        val request = AuthRequest(
            username = "user",
            password = "12345"
        )

        val expectedResponse = AuthResponse(
            token = "jwt123",
            type = "Bearer"
        )

        every { authService.login(request) } returns expectedResponse

        val result = authController.login(request)

        verify(exactly = 1) { authService.login(request) }
        assertNotNull(result)
        assertEquals("jwt123", result!!.token)
        assertEquals("Bearer", result.type)
    }

    @Test
    fun `deve retornar null quando login falhar`() {
        val request = AuthRequest(
            username = "user",
            password = "wrong"
        )

        every { authService.login(request) } returns null

        val result = authController.login(request)

        verify(exactly = 1) { authService.login(request) }
        assertEquals(null, result)
    }
}
