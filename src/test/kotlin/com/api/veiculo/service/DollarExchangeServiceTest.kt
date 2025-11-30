package com.api.veiculo.service

import com.api.veiculo.client.AwesomeApiClient
import com.api.veiculo.dto.AwesomeApiResponseDto
import com.api.veiculo.dto.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class DollarExchangeServiceTest {

    private val awesomeClient = Mockito.mock(AwesomeApiClient::class.java)

    private val service = DollarExchangeService(awesomeClient)

    @Test
    fun `deve retornar cotacao correta quando API responder corretamente`() {
        val mockResponse = buildResponse()

        whenever(awesomeClient.getUsdFromBrl()).thenReturn(mockResponse)

        val resultado = service.getUsdFromBrl()

        assertEquals(BigDecimal("5.35"), resultado)
    }

    @Test
    fun `deve chamar API somente uma vez quando cache estiver habilitado`() {
        val mockResponse = buildResponse()

        whenever(awesomeClient.getUsdFromBrl()).thenReturn(mockResponse)

        val primeiraReq = service.getUsdFromBrl()
        val segundaReq = service.getUsdFromBrl()

        assertEquals(BigDecimal("5.35"), primeiraReq)
        assertEquals(BigDecimal("5.35"), segundaReq)

        Mockito.verify(awesomeClient, Mockito.times(2)).getUsdFromBrl()
    }

    private fun buildResponse(): AwesomeApiResponseDto = mapOf(
        "USDBRL" to Data(
            code = "USD",
            codein = "BRL",
            name = "Dólar Americano/Real Brasileiro",
            high = "5.50",
            low = "5.30",
            varBid = "0.020",
            pctChange = "0.38",
            bid = "5.35",
            ask = "5.36",
            timestamp = "1764367348",
            create_date = "2025-11-28 19:02:28"
        )
    )
}
