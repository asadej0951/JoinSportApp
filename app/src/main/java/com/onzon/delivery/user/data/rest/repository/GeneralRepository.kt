package com.onzon.delivery.user.data.rest.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.onzon.delivery.user.data.local.Preferences
import com.onzon.delivery.user.data.rest.APIService
import com.onzon.delivery.user.data.rest.NetworkBoundResource
import com.onzon.delivery.user.vo.model.body.BodyLogin
import com.onzon.delivery.user.vo.model.body.BodyRegister
import com.onzon.delivery.user.vo.model.response.BaseResponse
import com.onzon.delivery.user.vo.model.response.ResponseLogin
import com.onzon.delivery.user.vo.model.response.ResponseOrderList
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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

    fun onRegister(data: BodyRegister, profileImage: File?) = object : NetworkBoundResource<BaseResponse>( ) {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<BaseResponse> =
            apiService.registerUser( "",
            onConvertFileToMultipartBody(profileImage!!, "profileImage"),
                onConvertObjectToMap(data))
    }.asLiveData()

    fun getOrderList(pageCurrent:Int) = object : NetworkBoundResource<ResponseOrderList>( ) {
        override fun saveCallResult(item: String) {}
        override fun createCall(): Observable<ResponseOrderList> = apiService.getOrderBookings("",preferences.getToken(),pageCurrent)
    }.asLiveData()


    private fun onConvertFileToMultipartBody(file: File,name:String): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

    private fun onConvertObjectToMap(fromValue: Any?): MutableMap<String, RequestBody?>? {
        val map = ObjectMapper().convertValue<MutableMap<String, Any?>>(fromValue, object : TypeReference<Map<String, Any?>>() {})
        while (map.values.remove(null));
        return onConvertMapToRequestBody(map)
    }

    private fun onConvertMapToRequestBody(map: MutableMap<String, Any?>): MutableMap<String, RequestBody?>? {
        val metadata = mutableMapOf<String, RequestBody?>()
        for (s in map.keys) {
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), map[s].toString())
            metadata[s] = requestBody
        }
        return metadata
    }
}