package com.api.veiculo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class VeiculoApplication

fun main(args: Array<String>) {
    runApplication<VeiculoApplication>(*args)
}