package com.api.veiculo.controller.request

import com.api.veiculo.enums.Roles

data class AuthRequest(
    val username: String,
    val password: String,
    val role: Roles? = null
)