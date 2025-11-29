package com.api.veiculo.client

import com.api.veiculo.dto.FrankfurterResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "frankfurterClient",
    url = "https://api.frankfurter.app",
)
interface FrankfurterClient {

    @GetMapping("/latest")
    fun getRate(
        @RequestParam("from") from: String,
        @RequestParam("to") to: String
    ): FrankfurterResponseDto
}