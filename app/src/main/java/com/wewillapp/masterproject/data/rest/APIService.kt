package com.wewillapp.masterproject.data.rest

import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
import io.reactivex.Observable
import retrofit2.http.*


interface APIService {

    @POST("shops/login")
    fun doLogin(@Body paramLogin: BodyLogin): Observable<ResponseLogin>

    @GET("shops/bookings")
    fun getOrderBookings(
        @Header("Accept-Language") language:String? = "en_US",
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int): Observable<ResponseOrderList>
}