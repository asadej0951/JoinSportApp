package com.onzon.delivery.user.data.rest

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException

sealed class ApiResponse<T> {
    companion object {
        fun onErrorResponseServer(e: Throwable): String {
            val mMessageError: String

            mMessageError = when (e) {
                is HttpException -> {
                    val responseBody = (e).response()!!.errorBody()
                    when {
                        e.code() in 400..499 -> {
                            JSONObject(JSONObject(responseBody!!.string()).get("errors").toString()).getString("message")
                        }
                        e.code() == 413 ->{
                            "413 Request Entity Too Large"
                        }
                        e.code() == 500 ->{
                            "Error HTTP 500 Internal Server Error"
                        }
                        else ->
                            getErrorMessage(
                                responseBody!!
                            )
                    }
                }else -> {
                    val responseBody = e.message
                    responseBody.toString()
                }
            }
            return mMessageError
        }
        private fun getErrorMessage(responseBody: ResponseBody): String {
            return try {
                val jsonObject = JSONObject(responseBody.string())
                jsonObject.getString("message")
            } catch (e: Exception) {
                "Something went wrong"
            }

        }
    }
}
