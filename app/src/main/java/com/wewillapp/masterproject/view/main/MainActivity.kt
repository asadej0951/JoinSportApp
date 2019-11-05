package com.wewillapp.masterproject.view.main

import android.content.Intent
import android.os.Bundle
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.utils.rxBus.RxBus
import com.wewillapp.masterproject.utils.rxBus.RxEvent
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.login.LoginActivity
import io.reactivex.disposables.Disposable

class MainActivity : BaseActivity() {

    private var tokenExpiredDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow()
        }

        onSubScriptTokenHasExpired()
    }

    private fun onSubScriptTokenHasExpired() {
        tokenExpiredDisposable = RxBus.listen(RxEvent::class.java).subscribe {
            if (it.event == "token has expired"){
                val intentLogin = Intent(getBaseActivity,LoginActivity::class.java)
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
