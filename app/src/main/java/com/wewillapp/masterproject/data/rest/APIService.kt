package com.wewillapp.masterproject.data.rest

import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    @POST("shops/login")
    fun doLogin(@Body paramLogin: BodyLogin): Observable<ResponseLogin>
}