package com.wewillapp.masterproject.utils

import android.content.Context
import android.content.Intent
import com.wewillapp.masterproject.utils.rxBus.RxBus
import com.wewillapp.masterproject.view.login.LoginActivity
import com.wewillapp.masterproject.vo.RxEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TokenExpired @Inject constructor(@ApplicationContext val context: Context) {

    private var tokenExpiredDisposable: Disposable? = null

    fun doCheckTokenExpire() {
        tokenExpiredDisposable = RxBus.listen(RxEvent::class.java).subscribe {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    fun onDestroyDisposable() {
        tokenExpiredDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }
}
