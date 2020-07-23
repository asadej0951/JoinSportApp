package com.wewillapp.masterproject.integrationtest

import com.wewillapp.masterproject.data.rest.APIService
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ApiClientTest : MockWebServerTest() {

    companion object {
        private const val ANY_TASK_ID = "1"
    }

    private lateinit var apiService: APIService

//    @Before
//    override fun setUp() {
//        super.setUp()
//
//        val mockWebServerEndpoint = baseEndpoint
//        apiService = APIService(mockWebServerEndpoint)
//    }
//
//    @Test
//    fun sendsAcceptAndContentTypeHeaders() {
//        enqueueMockResponse(200, "getTasksResponse.json")
//
//        apiClient.allTasks
//
//        assertRequestContainsHeader("Accept", "application/json")
//    }
//
//    @Test
//    fun sendsContentTypeHeader() {
//        enqueueMockResponse(200, "getTasksResponse.json")
//
//        apiClient.allTasks
//
//        assertRequestContainsHeader("Content-Type", "application/json")
//    }
//
//    @Test
//    fun sendsGetAllTaskRequestToTheCorrectEndpoint() {
//        enqueueMockResponse(200, "getTasksResponse.json")
//
//        apiClient.allTasks
//
//        assertGetRequestSentTo("/todos")
//    }

}