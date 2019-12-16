package com.onzon.delivery.user.view.main

import android.content.Intent
import android.os.Bundle
import com.onzon.delivery.user.R
import com.onzon.delivery.user.utils.rxBus.RxBus
import com.onzon.delivery.user.utils.rxBus.RxEvent
import com.onzon.delivery.user.view.base.BaseActivity
import com.onzon.delivery.user.view.login.LoginActivity
import io.reactivex.disposables.Disposable

class MainActivity : BaseActivity() {

    private var tokenExpiredDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                MainFragment.newInstance()
            ).commitNow()
        }

        onSubScriptTokenHasExpired()
    }

    private fun onSubScriptTokenHasExpired() {
        tokenExpiredDisposable = RxBus.listen(RxEvent::class.java).subscribe {
            if (it.event == "token has expired"){
                val intentLogin = Intent(getBaseActivity,
                    LoginActivity::class.java)
                intentLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentLogin)
                mUtils.eventStartAnimationIntent(this,false)
            }
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!tokenExpiredDisposable?.isDisposed!!) tokenExpiredDisposable!!.dispose() // clear subScript RX bus
    }
}
