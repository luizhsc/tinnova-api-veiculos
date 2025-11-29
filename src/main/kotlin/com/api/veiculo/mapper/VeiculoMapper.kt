package com.api.veiculo.mapper

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.entity.Veiculo
import com.mercadolivro.controller.response.VeiculoReponse

fun Veiculo.toResponse(): VeiculoReponse {
    return VeiculoReponse(
        id = this.id,
        nome = this.nome,
        marca = this.marca,
        cor = this.cor,
        ano = this.ano,
        placa = this.placa,
        valorMinimo = this.valorMinimo,
        valorMaximo = this.valorMaximo
    )
}

fun VeiculoRequest.toModel(id: Long? = null): Veiculo {
    return Veiculo(
        id = id,
        nome = this.nome!!,
        marca = this.marca!!,
        cor = this.cor!!,
        ano = this.ano!!,
        placa = this.placa!!,
        valorMinimo = this.valorMinimo!!,
        valorMaximo = this.valorMaximo!!,
        status = VeiculoStatus.ATIVO
    )
}
