package com.api.veiculo.dto


typealias AwesomeApiResponseDto = Map<String, Data>

data class Data(
    val code: String? = null,
    val codein: String? = null,
    val name: String? = null,
    val high: String? = null,
    val low: String? = null,
    val varBid: String? = null,
    val pctChange: String? = null,
    val bid: String? = null,
    val ask: String? = null,
    val timestamp: String? = null,
    val create_date: String? = null
)