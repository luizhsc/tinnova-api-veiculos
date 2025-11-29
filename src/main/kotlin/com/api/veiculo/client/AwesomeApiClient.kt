package com.api.veiculo.client

import com.api.veiculo.dto.AwesomeApiResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "awesomeApiClient",
    url = "https://economia.awesomeapi.com.br",
    fallback = AwesomeFallback::class
)
interface AwesomeApiClient {

    @GetMapping("/json/last/USD-BRL")
    fun getUsdFromBrl(): AwesomeApiResponseDto
}