package com.mercadolivro.controller.response

import java.math.BigDecimal

data class VeiculoReponse(
    var id: Long?,
    var nome: String,
    var marca: String,
    var cor: String,
    var ano: String,
    var placa: String,
    var valorMinimo: BigDecimal,
    var valorMaximo: BigDecimal,
)