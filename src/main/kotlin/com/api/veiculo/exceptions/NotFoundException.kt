package com.api.veiculo.exceptions

class NotFoundException(val errorCode: String, override val message: String) : Exception() {

}