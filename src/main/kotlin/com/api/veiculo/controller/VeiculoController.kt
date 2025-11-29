package com.api.veiculo.controller

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.mapper.toResponse
import com.api.veiculo.service.VeiculoService
import com.mercadolivro.controller.response.VeiculoReponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/veiculos")
class VeiculoController(
    val veiculoService: VeiculoService,
) {

    @GetMapping
    fun getAllByDetails(
        @RequestParam(required = false) marca: String?,
        @RequestParam(required = false) ano: String?,
        @RequestParam(required = false) cor: String?,
        @PageableDefault(page = 0, size = 10) pageable: Pageable,
    ): Page<VeiculoReponse> =
        veiculoService.findAllByDetails(marca, ano, cor, pageable)

    @GetMapping(params = ["valorMinimo", "valorMaximo"])
    fun getByValor(
        @RequestParam(required = false) valorMinimo: BigDecimal?,
        @RequestParam(required = false) valorMaximo: BigDecimal?,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<VeiculoReponse> =
        veiculoService.findByValor(valorMinimo, valorMaximo, pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): VeiculoReponse = veiculoService.findById(id).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: VeiculoRequest): VeiculoReponse =
        veiculoService.create(request)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Long, @RequestBody request: VeiculoRequest) =
        veiculoService.update(id, request)

    @PatchMapping("/{id}")
    fun updatePartial(
        @PathVariable id: Long,
        @RequestBody request: VeiculoRequest
    ): VeiculoReponse =
        veiculoService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) = veiculoService.delete(id)

    @GetMapping("/por-marca")
    fun reportByMarca(): List<VeiculosReportResponseDto> {
        return veiculoService.generateReportByMarca()
    }

}