package com.api.veiculo.client

import com.api.veiculo.dto.AwesomeApiResponseDto
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AwesomeFallback(
    private val frankFurterClient: FrankfurterClient
) : AwesomeApiClient {

    override fun getUsdFromBrl(): AwesomeApiResponseDto {
        val response = frankFurterClient.getRate("USD", "BRL").rates["BRL"]

        return AwesomeApiResponseDto(
            bid = response?.toBigDecimal() ?: BigDecimal.ZERO
        )
    }
}