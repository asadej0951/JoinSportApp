package com.onzon.delivery.user.view.base

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.onzon.delivery.user.data.local.Preferences
import com.onzon.delivery.user.utils.CheckPermission
import com.onzon.delivery.user.utils.Utils
import com.onzon.delivery.user.utils.dialog.DialogPresenter
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

    @Inject
    lateinit var mPreferences: Preferences

    @Inject
    lateinit var mCheckPermission: CheckPermission


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBaseActivity = this
        onSetEventView()
    }

    private fun onSetEventView() {

    }

    fun startIntentAnimation(isStatus: Boolean){
        mUtils.eventStartAnimationIntent(this,isStatus)
    }

    fun setTitleToolBar(textViewTitle: TextView, messageTitle:String) {
        textViewTitle.text = messageTitle
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

}