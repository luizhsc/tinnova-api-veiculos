package com.mercadolivro.controller.response

data class AuthResponse(val token: String, val type: String = "Bearer")