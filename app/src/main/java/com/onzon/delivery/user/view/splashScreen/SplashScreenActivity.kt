package com.onzon.delivery.user.view.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.onzon.delivery.user.R
import com.onzon.delivery.user.data.Constants
import com.onzon.delivery.user.databinding.ActivitySplashScreenBinding
import com.onzon.delivery.user.view.base.BaseActivity
import com.onzon.delivery.user.view.login.LoginActivity
import com.onzon.delivery.user.view.main.MainActivity
import qiu.niorgai.StatusBarCompat

class SplashScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashScreenBinding

    private var mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        StatusBarCompat.translucentStatusBar(this, true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
    }

    private fun initViewModel() {
        onCheckTokenIntentMain()
    }

    private fun onCheckTokenIntentMain() {
        startApp(if(mPreferences.getToken().isNotEmpty()) "intentMain" else "login")
    }

    private fun startApp(statusIntent: String) {
        var intent = Intent()
        when (statusIntent) {
            "login" -> {
                intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            }
            "intentMain" -> {
                intent = Intent(this, MainActivity::class.java)
            }
        }
        mHandler.postDelayed({
            startActivity(intent)
            finishAffinity()
        }, Constants.mTimeLoadPage)
    }

}
