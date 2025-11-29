package com.api.veiculo.service

import com.api.veiculo.client.AwesomeApiClient
import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.exceptions.NotFoundException
import com.api.veiculo.mapper.toModel
import com.api.veiculo.mapper.toResponse
import com.api.veiculo.entity.Veiculo
import com.api.veiculo.repository.VeiculoRepository
import com.mercadolivro.controller.response.VeiculoReponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class VeiculoService(
    private val veiculoRepository: VeiculoRepository,
    private val dollarExchangeService: DollarExchangeService
) {

    fun findAllByDetails(marca: String?, ano: String?, cor: String?, pageable: Pageable): Page<VeiculoReponse> {
        val filters = listOf(marca, ano, cor)
        return if (filters.any { it != null }) {
            veiculoRepository.findByDetalhes(marca, ano, cor, pageable).map { it.toResponse() }
        } else {
            veiculoRepository.findAll(pageable).map { it.toResponse() }
        }
    }

    fun findByValor(valorMinimo: BigDecimal?, valorMaximo: BigDecimal?, pageable: Pageable): Page<VeiculoReponse> {
        return veiculoRepository.findByFaixaDePreco(valorMinimo, valorMaximo, pageable).map { it.toResponse() }
    }

    fun create(request: VeiculoRequest): VeiculoReponse {
        val dolar = dollarExchangeService.getUsdFromBrl()

        request.valorMinimo = dolar.multiply(request.valorMinimo)
        request.valorMaximo = dolar.multiply(request.valorMaximo)

        return veiculoRepository.save(request.toModel()).toResponse()
    }

    fun findById(id: Long): Veiculo {
        return veiculoRepository.findById(id)
            .orElseThrow { NotFoundException("404", "Veiculo id $id not found") }
    }

    fun update(id: Long, veiculoRequest: VeiculoRequest): VeiculoReponse {
        val veiculo = findById(id).apply {
            nome = veiculoRequest.nome ?: nome
            marca = veiculoRequest.marca ?: marca
            cor = veiculoRequest.cor ?: cor
            ano = veiculoRequest.ano ?: ano
            placa = veiculoRequest.placa ?: placa
            valorMaximo = veiculoRequest.valorMaximo ?: valorMaximo
            valorMinimo = veiculoRequest.valorMinimo ?: valorMinimo
        }
        return veiculoRepository.save(veiculo).toResponse()
    }

    fun delete(id: Long) {
        val veiculo = findById(id)
        veiculo.status = VeiculoStatus.DELETADO
        veiculoRepository.save(veiculo)
    }

    fun generateReportByMarca(): List<VeiculosReportResponseDto> {
        return veiculoRepository.reportByMarca()
    }

    operator fun BigDecimal.times(other: Double): BigDecimal =
        this.multiply(other.toBigDecimal())

}
