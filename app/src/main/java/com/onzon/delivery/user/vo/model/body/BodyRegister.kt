package com.onzon.delivery.user.vo.model.body

data class BodyRegister(
    val email :String ,
    val fullname :String ,
    val password :String ,
    val passwordConfirmation :String,
    val language:String? = "th"
)