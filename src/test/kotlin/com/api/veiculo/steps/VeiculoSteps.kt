package com.api.veiculo.steps

import com.api.veiculo.controller.request.AuthRequest
import com.api.veiculo.controller.request.VeiculoRequest
import com.api.veiculo.enums.Roles
import com.api.veiculo.mapper.toResponse
import com.api.veiculo.service.AuthService
import com.api.veiculo.service.VeiculoService
import com.mercadolivro.controller.response.AuthResponse
import com.mercadolivro.controller.response.VeiculoReponse
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest


class VeiculoSteps {

    @Autowired
    lateinit var veiculoService: VeiculoService

    @Autowired
    lateinit var authService: AuthService

    private var pageable = PageRequest.of(0, 10)

    private lateinit var veiculoResponse: VeiculoReponse
    private lateinit var veiculoResponsePage: Page<VeiculoReponse>
    private lateinit var authResponse: AuthResponse
    private lateinit var veiculoRequest: VeiculoRequest

    @Dado("criar usuario admin")
    fun criarUsuarioAdmin() {
        assertNotNull(authService.register(AuthRequest(username = "admin", password = "admin", role = Roles.ADMIN)))
    }

    @Quando("autenticar com usuario admin válido")
    fun obterToken() {
        authResponse = authService.login(AuthRequest(username = "admin", password = "admin")).let { it as AuthResponse }
    }

    @Entao("retornar o token")
    fun validarToken() {
        assertNotNull(authResponse.token)
    }

    @Dado("um veiculo request com placa {string} e nome {string}")
    fun criarVeiculo(placa: String, nome: String) {
        veiculoRequest = buildVeiculoRequest(nome, placa)
    }

    @Quando("salvar veículo")
    fun salvarVeiculo() {
        veiculoResponse = veiculoService.create(veiculoRequest)
        assertNotNull(veiculoResponse)
    }

    @Entao("a resposta deve conter os dados do veiculo salvo")
    fun validarVeiculo() {
        assertEquals("Teste", veiculoResponse.nome)
        assertEquals("ABC1234", veiculoResponse.placa)
    }

    @Quando("listar veiculos filtrando por marca {string}")
    fun obterVeiculoPorMarca(marca: String) {
        veiculoResponsePage = veiculoService.findAllByDetails(marca, null, null, pageable)
        assertNotNull(veiculoResponsePage)
    }

    @Entao("a lista deve conter o veículo com placa {string}")
    fun validarVeiculoPorPlaca(placa: String) {
        assertEquals(placa, veiculoResponsePage.content[0].placa)
    }

    @Quando("buscar veículo por id {string}")
    fun obterVeiculoPorId(id: String) {
        veiculoResponse = veiculoService.findById(id.toLong()).toResponse()
        assertNotNull(veiculoResponse)
    }

    @Entao("os dados devem incluir cor Preto e ano 2025")
    fun validarVeiculoPorId() {
        assertEquals("Preto", veiculoResponse.cor)
        assertEquals("2025", veiculoResponse.ano)
    }

    private fun buildVeiculoRequest(nome: String, placa: String): VeiculoRequest =
        VeiculoRequest(
            nome = nome,
            marca = "Honda",
            cor = "Preto",
            ano = "2025",
            placa = placa,
            valorMinimo = 999.toBigDecimal(),
            valorMaximo = 9999.toBigDecimal(),
        )
}

