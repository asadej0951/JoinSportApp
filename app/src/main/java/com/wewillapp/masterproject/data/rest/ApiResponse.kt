package com.wewillapp.masterproject.data.rest

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException

sealed class ApiResponse<T> {
    companion object {
        fun onErrorResponseServer(e: Throwable): String {
            var mMessageError: String
            try {
                mMessageError = when (e) {
                    is HttpException -> {
                        val responseBody = (e).response()!!.errorBody()
                        val jObjError = JSONObject(responseBody!!.string())
                        when {
                            e.code() in 400..500 -> {
                                if (jObjError.getJSONObject("errors").getString("message").isNotEmpty())
                                    jObjError.getJSONObject("errors").getString("message").toString()
                                else
                                    "Something went wrong ${e.code()}"
                            }
                            else ->
                                getErrorMessage(responseBody)
                        }
                    }
                    else -> {
                        val responseBody = e.message
                        if (responseBody!!.contains("No address associated with hostname")) {
                            "กรุณาตรวจสอบการเชื่อมต่ออินเตอร์เน็ต"
                        } else
                            responseBody.toString()
                    }
                }
            }catch (ex:Exception){
                mMessageError = "Something went wrong ${e.message}"
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