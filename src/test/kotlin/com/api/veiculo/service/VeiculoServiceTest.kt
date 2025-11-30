package com.api.veiculo.service

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.entity.Veiculo
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.exceptions.NotFoundException
import com.api.veiculo.repository.VeiculoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.util.*

class VeiculoServiceTest {

    private val veiculoRepository: VeiculoRepository = mockk()
    private val dollarExchangeService: DollarExchangeService = mockk()

    private lateinit var service: VeiculoService

    @BeforeEach
    fun setup() {
        service = VeiculoService(veiculoRepository, dollarExchangeService)
    }

    @Test
    fun `deve retornar veiculos filtrados quando filtros forem informados`() {
        val pageable = PageRequest.of(0, 10)
        val veiculo = buildVeiculo()

        every {
            veiculoRepository.findByDetalhes("Toyota", null, null, pageable)
        } returns PageImpl(listOf(veiculo))

        val result = service.findAllByDetails("Toyota", null, null, pageable)

        assertEquals(1, result.content.size)
        assertEquals("Carro A", result.content[0].nome)
    }

    @Test
    fun `deve retornar todos veiculos quando nenhum filtro for informado`() {
        val pageable = PageRequest.of(0, 10)
        val veiculo = buildVeiculo().copy(id = 1L)

        every { veiculoRepository.findAll(pageable) } returns PageImpl(listOf(veiculo))

        val result = service.findAllByDetails(null, null, null, pageable)

        assertEquals(1, result.content.size)
    }

    @Test
    fun `deve buscar veiculos pela faixa de preco`() {
        val pageable = PageRequest.of(0, 10)
        val veiculo = buildVeiculo().copy(id = 1L)

        every {
            veiculoRepository.findByFaixaDePreco(BigDecimal(10), BigDecimal(20), pageable)
        } returns PageImpl(listOf(veiculo))

        val result = service.findByValor(BigDecimal(10), BigDecimal(20), pageable)

        assertEquals(1, result.content.size)
    }

    @Test
    fun `deve criar veiculo multiplicando valores pela cotacao do dolar`() {
        val request = buildVeiculoRequest()

        every { dollarExchangeService.getUsdFromBrl() } returns BigDecimal(5)

        val salvo = Veiculo(
            id = 1L,
            nome = request.nome,
            marca = request.marca,
            cor = request.cor,
            ano = request.ano,
            placa = request.placa,
            valorMinimo = BigDecimal(50),
            valorMaximo = BigDecimal(100),
            status = VeiculoStatus.ATIVO
        )

        every { veiculoRepository.save(any()) } returns salvo

        val result = service.create(request)

        assertEquals("Carro D", result.nome)
        assertEquals(BigDecimal(50), salvo.valorMinimo)
        assertEquals(BigDecimal(100), salvo.valorMaximo)
    }

    @Test
    fun `deve retornar veiculo quando id existir`() {
        val veiculo = buildVeiculo().copy(id = 1L)

        every { veiculoRepository.findById(1L) } returns Optional.of(veiculo)

        val result = service.findById(1L)

        assertEquals("Carro A", result.nome)
    }

    @Test
    fun `deve lancar NotFoundException quando id nao existir`() {
        every { veiculoRepository.findById(1L) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) {
            service.findById(1L)
        }
    }

    @Test
    fun `deve atualizar veiculo com sucesso`() {
        val existente = Veiculo(
            id = 1L,
            nome = "Carro F",
            marca = "BMW",
            cor = "Azul",
            ano = "2020",
            placa = "FFF0000",
            valorMinimo = BigDecimal(10),
            valorMaximo = BigDecimal(20),
            status = VeiculoStatus.ATIVO
        )

        val request = buildVeiculoRequest()

        every { veiculoRepository.findById(1L) } returns Optional.of(existente)
        every { veiculoRepository.save(any()) } returns existente

        val result = service.update(1L, request)

        assertEquals("Carro D", result.nome)
    }

    @Test
    fun `deve marcar veiculo como deletado`() {
        val existente = Veiculo(
            id = 1L,
            nome = "Carro Z",
            marca = "Kia",
            cor = "Preto",
            ano = "2020",
            placa = "PPP1234",
            valorMinimo = BigDecimal(10),
            valorMaximo = BigDecimal(20),
            status = VeiculoStatus.ATIVO
        )

        every { veiculoRepository.findById(1L) } returns Optional.of(existente)
        every { veiculoRepository.save(any()) } returns existente

        service.delete(1L)

        assertEquals(VeiculoStatus.DELETADO, existente.status)
    }

    @Test
    fun `deve gerar relatorio por marca`() {
        val report = listOf(VeiculosReportResponseDto("Toyota", 10))

        every { veiculoRepository.reportByMarca() } returns report

        val result = service.generateReportByMarca()

        assertEquals(1, result.size)
        assertEquals("Toyota", result[0].marca)
    }

    private fun buildVeiculo() = Veiculo(
        id = 1L,
        nome = "Carro A",
        marca = "Toyota",
        cor = "Azul",
        ano = "2020",
        placa = "ABC1234",
        valorMinimo = BigDecimal(10),
        valorMaximo = BigDecimal(20),
        status = VeiculoStatus.ATIVO
    )

    private fun buildVeiculoRequest() = VeiculoRequest(
        nome = "Carro D",
        marca = "Ford",
        cor = "Cinza",
        ano = "2022",
        placa = "EEE2222",
        valorMinimo = BigDecimal(10),
        valorMaximo = BigDecimal(20)
    )
}
