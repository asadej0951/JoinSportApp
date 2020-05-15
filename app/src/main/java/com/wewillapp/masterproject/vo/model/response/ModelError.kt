package com.wewillapp.masterproject.vo.model.response

data class ModelError(
    val errors: Errors
)

data class Errors(val message: String?)