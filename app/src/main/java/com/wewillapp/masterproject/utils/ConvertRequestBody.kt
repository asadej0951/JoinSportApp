package com.wewillapp.masterproject.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

object ConvertRequestBody {

    fun onConvertFileToMultipartBody(file: File, name: String): MultipartBody.Part {
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

    fun onConvertObjectToMap(fromValue: Any?): MutableMap<String, RequestBody?>? {
        val map = ObjectMapper().convertValue<MutableMap<String, Any?>>(
            fromValue,
            object : TypeReference<Map<String, Any?>>() {})
        return onConvertMapToRequestBody(map)
    }

    private fun onConvertMapToRequestBody(map: MutableMap<String, Any?>): MutableMap<String, RequestBody?>? {
        val metadata = mutableMapOf<String, RequestBody?>()
        for (s in map.keys) {
            val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), map[s].toString())
            metadata[s] = requestBody
        }
        return metadata
    }
}
