package com.wewillapp.masterproject.data.rest

import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.BaseResponse
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface APIService {

    @POST("shops/login")
    fun doLogin(@Body paramLogin: BodyLogin): Observable<ResponseLogin>

    @Multipart
    @POST("users/register")
    fun registerUser(
        @Header("Accept-Language") language: String? = "en_US",
        @Part profileImage: MultipartBody.Part?,
        @PartMap registerBody: MutableMap<String, @JvmSuppressWildcards RequestBody?>?
    ): Observable<BaseResponse>

    @GET("shops/bookings")
    fun getOrderBookings(
        @Header("Accept-Language") language: String? = "en_US",
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int
    ): Observable<ResponseOrderList>
}
