package com.onzon.delivery.user.vo.model.response

data class BaseResponse(
    val data: Data?,
    var message: String?
) {
    data class Data(
        val message: String?
    )
}