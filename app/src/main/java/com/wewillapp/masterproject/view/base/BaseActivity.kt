package com.wewillapp.masterproject.view.base

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.CheckPermission
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import qiu.niorgai.StatusBarCompat

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var toolbarViewModel: ToolbarViewModel

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var mUtils: Utils

    @Inject
    lateinit var mPreferences: Preferences

    @Inject
    lateinit var mCheckPermission: CheckPermission

    @Inject
    lateinit var mTokenExpiredDisposable: TokenExpired

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
