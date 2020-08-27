package com.wewillapp.masterproject.domain

import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.data.rest.NetworkBoundResource
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.body.BodyRegister
import com.wewillapp.masterproject.vo.model.response.BaseResponse
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
import io.reactivex.Observable
import java.io.File

class GeneralUseCase(
    private val generalRepository: GeneralRepository,
    private val preferences: Preferences
) {

    fun doLogin(paramLogin: BodyLogin) =
        object : NetworkBoundResource<ResponseLogin>() {
            override fun saveCallResult(item: String) {}
            override fun createCall(): Observable<ResponseLogin> =
                generalRepository.onLogin(paramLogin)
        }.asLiveData()


    fun doRegister(bodyRegister: BodyRegister, image: File?) =
        object : NetworkBoundResource<BaseResponse>() {
            override fun saveCallResult(item: String) {}
            override fun createCall(): Observable<BaseResponse> =
                generalRepository.onRegister(bodyRegister, image)
        }.asLiveData()


    fun getOrderList(pageCurrent: Int) = object : NetworkBoundResource<ResponseOrderList>() {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<ResponseOrderList> =
            generalRepository.getOrderList("th",pageCurrent, preferences.getToken())
    }.asLiveData()

}