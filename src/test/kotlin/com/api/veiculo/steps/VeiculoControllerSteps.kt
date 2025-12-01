package com.api.veiculo.steps

import com.api.veiculo.context.TokenContext
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class VeiculoControllerSteps {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var tokenContext: TokenContext

    private lateinit var result: MvcResult
    private lateinit var jsonRequest: String

    @Dado("que eu possuo um JSON válido de veículo")
    fun montarJson() {
        jsonRequest = """
            {
              "marca":"Honda",
              "nome":"Civic",
              "ano":2021,
              "cor":"Preto",
              "placa": "aaaaa",
              "valorMaximo": 1.0,
              "valorMinimo": 10.0
            }
        """.trimIndent()
    }

    @Quando("eu enviar a requisição para criar o veículo")
    fun criarVeiculo() {
        result = mockMvc.perform(
            post("/veiculos")
                .header("Authorization", "Bearer ${tokenContext.token}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()
    }

    @Então("o status da resposta deve ser {int}")
    fun validarStatus(statusEsperado: Int) {
        val statusAtual = result.response.status
        assertEquals(statusEsperado, statusAtual)
    }

    @Dado("que existe um veículo com ID {long}")
    fun mockVeiculo(id: Long) {
        jsonRequest = """
            {
              "marca":"Honda",
              "nome":"Civic",
              "ano":2021,
              "cor":"Preto",
              "placa": "aaaaa",
              "valorMaximo": 1.0,
              "valorMinimo": 10.0
            }
        """.trimIndent()
    }

    @Quando("eu buscar o veículo pelo ID {long}")
    fun buscarPorId(id: Long) {
        result = mockMvc.perform(
            get("/veiculos/$id")
                .header("Authorization", "Bearer ${tokenContext.token}")
        ).andReturn()
    }

    @Dado("que existem veículos cadastrados")
    fun mockListaVeiculos() {
    }

    @Quando("eu listar veículos filtrando por marca {string}")
    fun listarPorMarca(marca: String) {
        result = mockMvc.perform(
            get("/veiculos")
                .header("Authorization", "Bearer ${tokenContext.token}")
                .param("marca", marca)
        ).andReturn()
    }

    @Quando("eu atualizar completamente o veículo com ID {long}")
    fun atualizarCompleto(id: Long) {
        result = mockMvc.perform(
            put("/veiculos/$id")
                .header("Authorization", "Bearer ${tokenContext.token}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()
    }

    @Quando("eu atualizar parcialmente o veículo com ID {long}")
    fun atualizarParcial(id: Long) {
        result = mockMvc.perform(
            patch("/veiculos/$id")
                .header("Authorization", "Bearer ${tokenContext.token}")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"cor": "Azul"}""")
        ).andReturn()
    }

    @Quando("eu excluir o veículo com ID {long}")
    fun excluir(id: Long) {
        result = mockMvc.perform(
            delete("/veiculos/$id")
                .header("Authorization", "Bearer ${tokenContext.token}")
        ).andReturn()
    }

}
