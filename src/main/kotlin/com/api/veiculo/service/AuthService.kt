package com.api.veiculo.service

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.entity.User
import com.api.veiculo.repository.UserRepository
import com.api.veiculo.security.JwtUtil
import com.mercadolivro.controller.response.AuthResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(request: AuthRequest) {
        val encoded = passwordEncoder.encode(request.password)
        val user = User(username = request.username, password = encoded, role = request.role!!)
        userRepository.save(user)
    }

    fun login(request: AuthRequest): AuthResponse? {
        val user = userRepository.findByUsername(request.username) ?: return null
        return if (passwordEncoder.matches(request.password, user.password)) {
            AuthResponse(token = JwtUtil.generateToken(user.username, user.role))
        } else null
    }
}