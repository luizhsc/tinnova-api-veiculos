package com.api.veiculo.client

import com.api.veiculo.dto.AwesomeApiResponseDto
import com.api.veiculo.dto.Data
import org.springframework.stereotype.Component

@Component
class AwesomeFallback(
    private val frankFurterClient: FrankfurterClient
) : AwesomeApiClient {

    override fun getUsdFromBrl(): AwesomeApiResponseDto {
        val response = frankFurterClient.getRate()
        return mapOf(
            "USDBRL" to Data(bid = response.rates["BRL"].toString()),
        )
    }
}