package com.api.veiculo.service

import com.api.veiculo.client.AwesomeApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DollarExchangeService(
    private val awesomeClient: AwesomeApiClient,
) {

    @Cacheable(cacheNames = ["usdToBrl"], key = "'rate'")
    fun getUsdFromBrl(): BigDecimal {
        val response = awesomeClient.getUsdFromBrl()
        return response.getValue("USDBRL").bid!!.toBigDecimal()
    }
}