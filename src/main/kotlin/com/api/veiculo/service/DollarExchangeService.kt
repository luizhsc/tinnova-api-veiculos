package com.api.veiculo.service

import com.api.veiculo.client.AwesomeApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DollarExchangeService(
    private val awesomeClient: AwesomeApiClient
) {

    @Cacheable(cacheNames = ["usdToBrl"], key = "'rate'")
    fun getUsdFromBrl(): BigDecimal {
        return awesomeClient.getUsdFromBrl().bid
    }
}