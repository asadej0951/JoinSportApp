package com.wewillapp.masterproject.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.wewillapp.masterproject.base.BaseUTTest
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.di.module.networkModule
import com.wewillapp.masterproject.di.module.repositoryModule
import com.wewillapp.masterproject.di.module.utilityModule
import com.wewillapp.masterproject.di.module.viewModelModule
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.inject
import org.mockito.Mockito.`when`
import org.robolectric.res.android.Asset
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

        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        assertThat(testObserver.values()[0].data, `is`(notNullValue()))
    }

    @Test
    fun testLoginCheckName_returnResponseFullName() =  runBlocking {
        mockNetworkResponseWithFileContent("objectLogin.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.onLogin(BodyLogin(userName,
            password,token,language))
        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        assertThat(testObserver.values()[0].data.fullname, `is`("Vittavach"))
    }

    @Test
    fun testLoginError_returnNoResponse() =  runBlocking {
        mockNetworkResponseWithFileContent("objectLogin.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.onLogin(BodyLogin("gobank2@gmail.com",
            "password","","th"))
        val testObserver = TestObserver<ResponseLogin>()
        dataReceived.subscribe(testObserver)

        assertThat(testObserver.values().isEmpty(), `is`(true))
    }

    @After
    override fun tearDown() {
        stopKoin()
    }

}