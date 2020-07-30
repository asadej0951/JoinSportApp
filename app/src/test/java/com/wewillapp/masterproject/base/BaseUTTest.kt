package com.wewillapp.masterproject.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File
import java.io.InputStream

/**
 * Creates base
 */
abstract class BaseUTTest : KoinTest {

    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    /**
     * Default, let server be shut down
     */
    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mMockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    /**
     * Reads input file and converts to json
     */
//    fun getJson(path : String) : String {
//        val uri = "com.wewillapp.masterproject/resources/"+path
//        val file = File(uri)
//        return String(file.readBytes())
//    }

    @Throws(IOException::class)
    open fun getJson(filename: String): String {
        val `is`: InputStream? = javaClass.getResourceAsStream(filename)
        val stringBuilder = StringBuilder()
        var i: Int
        val b = ByteArray(4096)
        if (`is` != null) {
            while (`is`.read(b).also { i = it } != -1) {
                stringBuilder.append(String(b, 0, i))
            }
        }
        return stringBuilder.toString()
    }

    /**
     * Start Mockwebserver
     */
    private fun startMockServer(shouldStart:Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    /**
     * Stop Mockwebserver
     */
    private fun stopMockServer() {
        if (mShouldStart){
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown(){
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }
}