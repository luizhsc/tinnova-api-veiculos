package com.api.veiculo.service

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.entity.User
import com.api.veiculo.repository.UserRepository
import com.api.veiculo.security.JwtUtil
import com.mercadolivro.controller.response.AuthResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import com.api.veiculo.exceptions.AccessDeniedException
import com.api.veiculo.security.CustomUserDetailsService

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(request: AuthRequest) {
        val encoded = passwordEncoder.encode(request.password)
        val user = User(username = request.username, password = encoded, role = request.role!!)
        userRepository.save(user)
    }

    fun login(request: AuthRequest): AuthResponse? {
        val user = customUserDetailsService.loadUserByUsername(request.username)
        return if (passwordEncoder.matches(request.password, user.password)) {
            AuthResponse(
                token = JwtUtil.generateToken(
                    user.username,
                    user.authorities.map { it.authority.removePrefix("ROLE_") })
            )
        } else {
            throw AccessDeniedException()
        }
    }
}