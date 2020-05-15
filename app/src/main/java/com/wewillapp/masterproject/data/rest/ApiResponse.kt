package com.wewillapp.masterproject.data.rest

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.wewillapp.masterproject.vo.model.response.ModelError
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException

object ApiResponse {

    private val mGson = Gson()

    fun onErrorResponseServer(e: Throwable): String {
        val mMessageError: String

        mMessageError = when (e) {
            is retrofit2.HttpException -> {
                val responseBody = (e).response()!!
                if (responseBody.code() == 401){
                    "401"
                }else {
                    val dataMessage = responseBody.errorBody()!!.string()
                    deseRializeObject(dataMessage)
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
        return mMessageError
    }

    private fun deseRializeObject(errorString: String): String {
        return try {
            mGson.fromJson(errorString, ModelError::class.java).errors.message
                ?: "som ting went wrong"
        } catch (e: JsonSyntaxException) {
            e.message.toString()
        }
    }
}