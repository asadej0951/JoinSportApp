package com.wewillapp.masterproject.view.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wewillapp.masterproject.util.Utils
import com.wewillapp.masterproject.util.dialog.DialogPresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var getBaseActivity: Context

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var mUtils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBaseActivity = this
        onEventView()
    }

    private fun onEventView() {
        mUtils.onSetStatusBar(this,true)
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

}