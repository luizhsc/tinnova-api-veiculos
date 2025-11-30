package com.api.veiculo.controller

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.entity.Veiculo
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.security.CustomUserDetailsService
import com.api.veiculo.security.JwtFilter
import com.api.veiculo.security.JwtUtil
import com.api.veiculo.service.VeiculoService
import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.controller.response.VeiculoReponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(VeiculoController::class)
@AutoConfigureMockMvc(addFilters = false)
class VeiculoControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {
    @MockBean
    lateinit var customUserDetailsService: CustomUserDetailsService

    @MockBean
    lateinit var jwtFilter: JwtFilter

    @MockBean
    lateinit var jwtUtil: JwtUtil

    @MockBean
    lateinit var veiculoService: VeiculoService

    @Test
    fun `deve retornar lista de veiculos`() {
        val response = buildVeiculoResponse()
        val pagedResult: Page<VeiculoReponse> = PageImpl(listOf(response))

        whenever(veiculoService.findAllByDetails(anyOrNull(), anyOrNull(), anyOrNull(), any()))
            .thenReturn(pagedResult)

        mockMvc.perform(get("/veiculos"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].marca").value("ante"))
    }

    @Test
    fun `deve filtrar por faixa de valores`() {
        val response = buildVeiculoResponse()

        val pagedResult: Page<VeiculoReponse> = PageImpl(listOf(response))

        whenever(veiculoService.findByValor(any(), any(), any()))
            .thenReturn(pagedResult)

        mockMvc.perform(
            get("/veiculos")
                .param("valorMinimo", "10000")
                .param("valorMaximo", "60000")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].nome").value("reprehendunt"))
    }

    @Test
    fun `deve retornar veiculo por id`() {
        val response = buildVeiculo()

        whenever(veiculoService.findById(1L)).thenReturn(response)

        mockMvc.perform(get("/veiculos/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nome").value("habeo"))
    }

    @Test
    fun `deve criar um veiculo`() {
        val request = buildVeiculoRequest()
        val response = buildVeiculoResponse()

        whenever(veiculoService.create(any())).thenReturn(response)

        mockMvc.perform(
            post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(2671))
    }

    @Test
    fun `deve atualizar um veiculo`() {
        val request = buildVeiculoRequest()

        mockMvc.perform(
            put("/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)

        Mockito.verify(veiculoService).update(1L, request)
    }

    @Test
    fun `deve atualizar parcialmente um veiculo`() {
        val request = buildVeiculoRequest()
        val response = buildVeiculoResponse()

        whenever(veiculoService.update(1L, request)).thenReturn(response)

        mockMvc.perform(
            patch("/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nome").value("reprehendunt"))
    }

    @Test
    fun `deve deletar um veiculo`() {
        mockMvc.perform(delete("/veiculos/1"))
            .andExpect(status().isNoContent)

        Mockito.verify(veiculoService).delete(1L)
    }

    @Test
    fun `deve retornar relatorio por marca`() {
        val report = listOf(
            VeiculosReportResponseDto("Toyota", 5),
            VeiculosReportResponseDto("Honda", 3)
        )

        whenever(veiculoService.generateReportByMarca()).thenReturn(report)

        val result = mockMvc.perform(get("/veiculos/por-marca"))
            .andExpect(status().isOk)
            .andReturn()

        val json = result.response.contentAsString
        val parsed = objectMapper.readValue(json, Array<VeiculosReportResponseDto>::class.java)

        assertEquals(2, parsed.size)
        assertEquals("Toyota", parsed[0].marca)
    }

    private fun buildVeiculoResponse() = VeiculoReponse(
        id = 2671,
        nome = "reprehendunt",
        marca = "ante",
        cor = "eius",
        ano = "malesuada",
        placa = "usu",
        valorMinimo = BigDecimal(40000),
        valorMaximo = BigDecimal(55000)
    )

    private fun buildVeiculoRequest() = VeiculoRequest(
        nome = "fabulas",
        marca = "vidisse",
        cor = "minim",
        ano = "netus",
        placa = "cum",
        valorMinimo = BigDecimal(40000),
        valorMaximo = BigDecimal(55000)
    )

    private fun buildVeiculo() = Veiculo(
        id = 7511,
        nome = "habeo",
        marca = "aliquip",
        cor = "sapientem",
        ano = "conclusionemque",
        placa = "sumo",
        valorMinimo = BigDecimal(40000),
        valorMaximo = BigDecimal(55000),
        status = VeiculoStatus.ATIVO
    )
}
