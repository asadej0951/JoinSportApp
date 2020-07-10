package com.onzon.delivery

import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.view.login.LoginViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ExampleUnitTest {

    @Mock
    private lateinit var generalRepository: GeneralRepository
    private lateinit var presenter: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginViewModel(generalRepository)
    }

    @Test
    fun `Should show error when username is empty`() {
        presenter.etUserName.set("")
        presenter.etPassWord.set("")
        presenter.mLoginCall.call()
        assertEquals(false, "")
    }
}
