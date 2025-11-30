package com.api.veiculo.controller

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.mapper.toResponse
import com.api.veiculo.service.VeiculoService
import com.mercadolivro.controller.response.VeiculoReponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/veiculos")
@Tag(
    name = "Veículos API",
    description = "Operações relacionadas a veículos"
)
@SecurityRequirement(name = "bearerToken")
class VeiculoController(
    val veiculoService: VeiculoService,
) {

    @GetMapping
    @Operation(
        summary = "Lista todos os veículos",
        description = "Permite filtrar por marca, ano e cor, com paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso")
        ]
    )
    fun getAllByDetails(
        @Parameter(description = "Filtrar pela marca")
        @RequestParam(required = false) marca: String?,

        @Parameter(description = "Filtrar pelo ano")
        @RequestParam(required = false) ano: String?,

        @Parameter(description = "Filtrar pela cor")
        @RequestParam(required = false) cor: String?,

        @Parameter(
            description = "Parâmetros de paginação e ordenação. Exemplo: page=0, size=10, sort=id,asc",
        )
        @PageableDefault(page = 0, size = 10) pageable: Pageable,
    ): Page<VeiculoReponse> =
        veiculoService.findAllByDetails(marca, ano, cor, pageable)

    @GetMapping(params = ["valorMinimo", "valorMaximo"])
    @Operation(
        summary = "Busca veículos por faixa de valores",
        description = "Filtra veículos pelo preço mínimo e máximo, com paginação."
    )
    fun getByValor(
        @Parameter(description = "Valor mínimo")
        @RequestParam(required = false) valorMinimo: BigDecimal?,

        @Parameter(description = "Valor máximo")
        @RequestParam(required = false) valorMaximo: BigDecimal?,

        @Parameter(
            description = "Parâmetros de paginação. Ex.: page=0, size=10, sort=preco,asc"
        )
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<VeiculoReponse> =
        veiculoService.findByValor(valorMinimo, valorMaximo, pageable)

    @GetMapping("/{id}")
    @Operation(summary = "Busca veículo por ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            ApiResponse(responseCode = "404", description = "Veículo não encontrado")
        ]
    )
    fun getById(
        @Parameter(description = "ID do veículo")
        @PathVariable id: Long
    ): VeiculoReponse =
        veiculoService.findById(id).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo veículo")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Veículo criado com sucesso")
        ]
    )
    fun create(
        @Parameter(description = "JSON com os dados do veículo a ser criado")
        @Valid @RequestBody request: VeiculoRequest
    ): VeiculoReponse =
        veiculoService.create(request)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualiza todos os dados de um veículo existente")
    fun update(
        @Parameter(description = "ID do veículo")
        @PathVariable id: Long,

        @Parameter(description = "JSON com os novos dados")
        @RequestBody request: VeiculoRequest
    ) = veiculoService.update(id, request)

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um veículo")
    fun updatePartial(
        @Parameter(description = "ID do veículo")
        @PathVariable id: Long,

        @Parameter(description = "Atributos a serem atualizados")
        @RequestBody request: VeiculoRequest
    ): VeiculoReponse =
        veiculoService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um veículo pelo ID")
    fun deleteById(
        @Parameter(description = "ID do veículo")
        @PathVariable id: Long
    ) = veiculoService.delete(id)

    @GetMapping("/por-marca")
    @Operation(summary = "Relatório agrupado por marca")
    fun reportByMarca(): List<VeiculosReportResponseDto> =
        veiculoService.generateReportByMarca()

}
