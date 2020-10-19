package com.wewillapp.masterproject.view.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.CheckPermission
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import qiu.niorgai.StatusBarCompat

abstract class BaseActivity : AppCompatActivity() {


    val toolbarViewModel: ToolbarViewModel by viewModel()

    val mUtils: Utils by inject()

    val mPreferences: Preferences by inject()

    val mCheckPermission: CheckPermission by inject { parametersOf(this) }

    val mTokenExpiredDisposable: TokenExpired by inject()

    val mDialogPresenter: DialogPresenter by inject {  parametersOf(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun onSetStatusBar() {
        StatusBarCompat.translucentStatusBar(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun onSetFullScreenStatusBar() {
        StatusBarCompat.translucentStatusBar(this)
        StatusBarCompat.translucentStatusBar(this, true)
    }

    fun startIntentAnimation(isStatus: Boolean) {
        mUtils.eventStartAnimationIntent(this, isStatus)
    }

    fun setTitleToolBar(textViewTitle: TextView, messageTitle: String) {
        textViewTitle.text = messageTitle
    }
}
