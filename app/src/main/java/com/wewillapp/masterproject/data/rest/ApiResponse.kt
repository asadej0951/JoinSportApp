package com.wewillapp.masterproject.data.rest

import com.wewillapp.masterproject.utils.MyLog
import com.wewillapp.masterproject.utils.rxBus.EventBusManage
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException
import java.util.regex.Pattern

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return if (error is UnknownHostException)
                ApiErrorResponse("No Internet Connection")
            else ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers().get("link")
                    )
                }
            } else {
               if (response.code() == 404)
                   EventBusManage.onAddEventRxBus("token has expired")

                val msg = when {
                    response.code() in 400..499 -> JSONObject(JSONObject(response.errorBody()?.string()).get("errors").toString()).getString("message")
                    response.code() == 413 -> "413 Request Entity Too Large"
                    response.code() == 500 -> "Error HTTP 500 Internal Server Error"
                    else ->
                        try {
                            JSONObject(response.errorBody()?.string()).getString("message")
                        }catch (ex:Exception) {
                            "Something went wrong"
                        }
                }

                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}


class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    MyLog.i("cannot parse next page from %s $next")
                    null
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()