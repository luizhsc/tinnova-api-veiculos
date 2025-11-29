package com.api.veiculo.entity

import com.api.veiculo.enums.VeiculoStatus
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "tbl_veiculo")
data class Veiculo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @Column var nome: String,

    @Column var marca: String,

    @Column var cor: String,

    @Column var ano: String,

    @Column var placa: String,

    @Column var valorMinimo: BigDecimal,

    @Column var valorMaximo: BigDecimal,

    @Column
    @Enumerated(EnumType.STRING)
    var status: VeiculoStatus,

    )