package com.api.veiculo.entity

import com.api.veiculo.enums.Roles
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val username: String,

    val password: String,

    @Column
    @Enumerated(EnumType.STRING)
    var role: Roles,
)
