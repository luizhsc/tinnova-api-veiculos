package com.api.veiculo.controller.request

import java.math.BigDecimal

data class VeiculoRequest(
    val nome: String?,
    val marca: String?,
    val cor: String?,
    val ano: String?,
    val placa: String?,
    var valorMaximo: BigDecimal?,
    var valorMinimo: BigDecimal?
)
