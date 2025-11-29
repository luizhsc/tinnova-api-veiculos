package com.api.veiculo.security

import com.api.veiculo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado")
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf(org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_${user.role}"))
        )
    }
}