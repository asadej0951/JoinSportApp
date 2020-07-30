package com.wewillapp.masterproject.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wewillapp.masterproject.base.BaseUTTest
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.di.module.networkModule
import com.wewillapp.masterproject.di.module.repositoryModule
import com.wewillapp.masterproject.di.module.utilityModule
import com.wewillapp.masterproject.di.module.viewModelModule
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class LoginViewModelTest: BaseUTTest() {

    private val generalRepository: GeneralRepository  by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var userName = "gobank@gmail.com"
    private var password = "password"
    private var token = "7C57196B27D826A2165F382821CF37C57196B27D826A2165F382821CF3"
    private var language = "th"

    @Before
    fun start(){
        super.setUp()
        startKoin {
            modules(arrayListOf(networkModule, utilityModule, repositoryModule, viewModelModule))
        }
    }

    @Test
    fun testLoginSuccess_returnResponse() =  runBlocking {
        mockNetworkResponseWithFileContent("objectLogin.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.onLogin(BodyLogin(userName, password,token,language))
        dataReceived.subscribe()
        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        Assert.assertNotNull(testObserver.values()[0].data)
    }

    @Test
    fun testLoginCheckName_returnResponseFullName() =  runBlocking {
        mockNetworkResponseWithFileContent("objectLogin.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.onLogin(BodyLogin(userName,
            password,token,language))
        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        Assert.assertEquals(testObserver.values()[0].data.fullname,"Vittavach")
    }

    @Test
    fun testLoginError_returnNoResponse() =  runBlocking {
        mockNetworkResponseWithFileContent("objectLogin.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.onLogin(BodyLogin("gobank2@gmail.com",
            "password","","th"))
        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        Assert.assertNotNull(testObserver.values())
    }


}