package com.api.veiculo.mapper

import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.model.VeiculoModel
import com.mercadolivro.controller.response.VeiculoReponse

fun VeiculoModel.toResponse(): VeiculoReponse {
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

fun VeiculoRequest.toModel(id: Long? = null): VeiculoModel {
    return VeiculoModel(
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
