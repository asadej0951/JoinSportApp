package com.wewillapp.masterproject.data.rest.repository

import com.wewillapp.masterproject.data.rest.APIService
import com.wewillapp.masterproject.data.rest.NetworkBoundResource
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import io.reactivex.Observable
import com.wewillapp.masterproject.data.local.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneralRepository
@Inject
constructor(
    private val apiService: APIService,
    private val preferences : Preferences
) {

    fun onLogin(paramLogin: BodyLogin) = object : NetworkBoundResource<ResponseLogin>( ) {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<ResponseLogin> = apiService.doLogin(paramLogin)
    }.asLiveData()

}