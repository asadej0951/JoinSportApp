package com.wewillapp.masterproject.data.rest.repository

import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.data.rest.APIService
import com.wewillapp.masterproject.data.rest.NetworkBoundResource
import com.wewillapp.masterproject.utils.ConvertRequestBody.onConvertFileToMultipartBody
import com.wewillapp.masterproject.utils.ConvertRequestBody.onConvertObjectToMap
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.body.BodyRegister
import com.wewillapp.masterproject.vo.model.response.BaseResponse
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneralRepository
@Inject
constructor(
    private val apiService: APIService,
    private val preferences: Preferences
) {

    fun onLogin(paramLogin: BodyLogin) = object : NetworkBoundResource<ResponseLogin>() {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<ResponseLogin> = apiService.doLogin(paramLogin)
    }.asLiveData()

    fun onRegister(data: BodyRegister, profileImage: File?) = object : NetworkBoundResource<BaseResponse>() {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<BaseResponse> =
            apiService.registerUser("",
            onConvertFileToMultipartBody(profileImage!!, "profileImage"), onConvertObjectToMap(data))
    }.asLiveData()

    fun getOrderList(pageCurrent: Int) = object : NetworkBoundResource<ResponseOrderList>() {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<ResponseOrderList> = apiService.getOrderBookings("", preferences.getToken(), pageCurrent)
    }.asLiveData()
}
