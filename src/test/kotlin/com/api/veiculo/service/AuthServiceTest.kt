package com.api.veiculo.service

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.enums.Roles
import com.api.veiculo.repository.UserRepository
import com.api.veiculo.security.CustomUserDetailsService
import com.api.veiculo.security.JwtUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var customUserDetailsService: CustomUserDetailsService
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var authService: AuthService
    private lateinit var jwtUtilMock: MockedStatic<JwtUtil>

    @BeforeEach
    fun setup() {
        userRepository = mock()
        customUserDetailsService = mock()
        passwordEncoder = mock()
        authService = AuthService(userRepository, customUserDetailsService, passwordEncoder)
        jwtUtilMock = Mockito.mockStatic(JwtUtil::class.java)
    }

    @Test
    fun `deve registrar um usuario`() {
        val request = AuthRequest(
            username = "joao",
            password = "123",
            role = Roles.USER
        )

        `when`(passwordEncoder.encode("123"))
            .thenReturn("encoded123")

        `when`(userRepository.save(Mockito.any()))
            .thenAnswer { it.arguments[0] }

        authService.register(request)

        Mockito.verify(userRepository, Mockito.times(1))
            .save(Mockito.argThat { user ->
                user.username == "joao" &&
                        user.password == "encoded123" &&
                        user.role == Roles.USER
            })
    }

}
