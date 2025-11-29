package com.api.veiculo.exceptions

import com.api.veiculo.dto.ErrorResponseDto
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponseDto> {
        val erro = ErrorResponseDto(
            code = HttpStatus.CONFLICT.name,
            message = "Erro de Integridade"
        )
        return ResponseEntity(erro, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleUnauthorized(ex: AuthenticationException): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = HttpStatus.UNAUTHORIZED.name,
            message = ex.message ?: "Não autorizado",
        )
        return ResponseEntity(error, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleForbidden(ex: AccessDeniedException): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = HttpStatus.FORBIDDEN.name,
            message = "Acesso negado",
        )
        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }

}