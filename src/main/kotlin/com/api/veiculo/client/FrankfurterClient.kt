package com.api.veiculo.client

import com.api.veiculo.dto.FrankfurterResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "frankfurterClient",
    url = "https://api.frankfurter.app",
)
interface FrankfurterClient {
    @GetMapping("/latest?from=USD&to=BRL")
    fun getRate(): FrankfurterResponseDto
}