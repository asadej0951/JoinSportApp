package com.wewillapp.masterproject.data.rest.repository

import com.wewillapp.masterproject.data.rest.APIService
import com.wewillapp.masterproject.utils.ConvertRequestBody.onConvertFileToMultipartBody
import com.wewillapp.masterproject.utils.ConvertRequestBody.onConvertObjectToMap
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.body.BodyRegister
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import io.reactivex.Observable
import java.io.File

class GeneralRepository
constructor(
    private val apiService: APIService
) {

    fun onLogin(paramLogin: BodyLogin): Observable<ResponseLogin> {
        return apiService.doLogin(paramLogin)
    }


    fun onRegister(data: BodyRegister, profileImage: File?) =
        apiService.registerUser(
            "th",
            onConvertFileToMultipartBody(profileImage!!, "profileImage"),
            onConvertObjectToMap(data)
        )

    fun getOrderList(language: String, pageCurrent: Int, token: String) =
        apiService.getOrderBookings(language, token, pageCurrent)
}
