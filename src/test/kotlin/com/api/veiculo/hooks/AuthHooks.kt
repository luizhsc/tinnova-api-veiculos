package com.api.veiculo.hooks

import com.api.veiculo.context.TokenContext
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import io.cucumber.java.Before

class AuthHooks {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var tokenContext: TokenContext

    @Before
    fun gerarTokenAntesDosCenarios() {
        if (!tokenContext.token.isNullOrBlank()) return

        val jsonPayload = """
            {
              "username": "admin",
              "password": "admin",
              "role": "ADMIN"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)
        ).andReturn()

        val loginJson = """
            {
              "username": "admin",
              "password": "admin"
            }
        """.trimIndent()

        val result = mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson)
        ).andReturn()

        val body = result.response.contentAsString

        val token = JSONObject(body).getString("token")

        tokenContext.token = token
    }
}