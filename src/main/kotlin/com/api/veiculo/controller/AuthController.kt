package com.api.veiculo.controller

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.service.AuthService
import com.mercadolivro.controller.response.AuthResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: AuthRequest) =
        authService.register(request)

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): AuthResponse? {
        return authService.login(request)
    }
}