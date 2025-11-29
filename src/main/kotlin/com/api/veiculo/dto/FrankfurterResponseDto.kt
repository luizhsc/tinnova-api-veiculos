package com.api.veiculo.dto

import java.math.BigDecimal

data class FrankfurterResponseDto(
    val amount: BigDecimal,
    val base: String,
    val date: String,
    val rates: Map<String, BigDecimal>
)