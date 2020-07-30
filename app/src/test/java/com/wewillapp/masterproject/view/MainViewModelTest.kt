package com.wewillapp.masterproject.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wewillapp.masterproject.base.BaseUTTest
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.di.module.networkModule
import com.wewillapp.masterproject.di.module.repositoryModule
import com.wewillapp.masterproject.di.module.utilityModule
import com.wewillapp.masterproject.di.module.viewModelModule
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
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
class MainViewModelTest : BaseUTTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val generalRepository: GeneralRepository by inject()

    private var token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjdjYTRlNzQ" +
            "3YjhiNDEzMTAyNzgxNTVkM2IyNTc5ZDUyOTdiMTJhYWY4OTdlYmMyMGRhMjEwZjNkNDgwNmM5NDhkYjBhYWMwN" +
            "2RiMmNlM2U2In0.eyJhdWQiOiIxIiwianRpIjoiN2NhNGU3NDdiOGI0MTMxMDI3ODE1NWQzYjI1NzlkNTI5N2IxMm" +
            "FhZjg5N2ViYzIwZGEyMTBmM2Q0ODA2Yzk0OGRiMGFhYzA3ZGIyY2UzZTYiLCJpYXQiOjE1OTU1ODIwMjAsIm5iZiI6MTU5N" +
            "TU4MjAyMCwiZXhwIjoxNjI3MTE4MDIwLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.fPC17E22MPDfLtaoW_osBPtbR-" +
            "vx6lzihIkbt9RCj0cmp5aFPlfm14WZ6uzpl0QyNsRxayvPRlk5IQcng_tasbVUdgCpRJtzU0Z9ItpHgad398L_Mm826Y-" +
            "eRDgfrynUm_SD6M8nmDqPUL1DepNpD1I7fuvzrGjld5zr3MPjwygdc1Txs6xcdFK_QRzegJC-p7iMgDU9-ZbTemrLhvqzH_2" +
            "6Ny2BkBGO5D_-_GdlCKXW7Wp8vayxDjPBbAZk-2kZxYW6tv-v-RfMJkckaXxlV8E4z5l4To5N1cF7u2Rb9jkyn4I4Fm_-T6HxDsW5-" +
            "BYFbYDWOe7GukwWlfgOBtTNUa6n1Amf-8QY8aMnzLMwi-bkkli9J4BPUE-TOB2zqWB_Ztv1aT-bVYa0yFFB2-Ri_4NOUTJ-" +
            "ie5lq5Tb2Ak17U9sAJD9vSM2GcTLu7H8RBNli5nQcZSXlwjqhsVKcU5NkA7f7DwHUkMD2-7REcDezInl3W8MwKNYZbsr" +
            "CqpCyePyJXix4vt8NSl48FbFt74GGlp0FPx0FUxWR_AX7-oQKnEFWvpwOlk4kq4gcfF2NgK0sQlRAgrs2q8D5Z9HDm4sSsE81hSr" +
            "UmtaWQ7TmGwbGAiqupFwxJcuA2pd6AlVLq4cc14hTE8sJvy9cYaskc_zeDPn5RJlcRl-ld5bfLiEyN8"

    @Before
    fun start() {
        super.setUp()
        startKoin {
            modules(arrayListOf(networkModule, utilityModule, repositoryModule, viewModelModule))
        }
    }

    @Test
    fun testOrderListSuccess_returnResponse() = runBlocking {
        mockNetworkResponseWithFileContent("objectOrderList.json", HttpURLConnection.HTTP_OK)

        val dataReceived = generalRepository.getOrderList("TH", 1, token)
        dataReceived.subscribe()
        val testObserver = TestObserver<ResponseOrderList>()
        dataReceived.subscribe(testObserver)

        Assert.assertNotNull(testObserver.values()[0].data)
    }
}