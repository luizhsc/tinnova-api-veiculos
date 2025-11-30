package com.api.veiculo.controller

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.service.AuthService
import com.mercadolivro.controller.response.AuthResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Autenticação",
    description = "Endpoints para registro e login de usuários"
)
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário na plataforma."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Usuário criado com sucesso"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Dados inválidos",
                content = [Content()]
            )
        ]
    )
    fun register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados para criar o usuário",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AuthRequest::class),
                    examples = [
                        ExampleObject(
                            name = "Exemplo Register",
                            value = """
                                {
                                  "username": "usuario",
                                  "password": "12345"
                                }
                            """
                        )
                    ]
                )
            ]
        )
        @RequestBody request: AuthRequest
    ) = authService.register(request)

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login",
        description = "Autentica um usuário e retorna um JWT."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Login bem-sucedido",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AuthResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Exemplo Login",
                                value = """
                                    {
                                      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
                                      "type": "Bearer"
                                    }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Credenciais inválidas",
                content = [Content()]
            )
        ]
    )
    fun login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciais para login",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AuthRequest::class),
                    examples = [
                        ExampleObject(
                            name = "Exemplo Login",
                            value = """
                                {
                                  "username": "usuario",
                                  "password": "123"
                                }
                            """
                        )
                    ]
                )
            ]
        )
        @RequestBody request: AuthRequest
    ): AuthResponse? {
        return authService.login(request)
    }
}