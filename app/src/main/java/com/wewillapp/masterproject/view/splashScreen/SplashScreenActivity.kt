package com.wewillapp.masterproject.view.splashScreen

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.data.Constants
import com.wewillapp.masterproject.databinding.ActivitySplashScreenBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.login.LoginActivity
import com.wewillapp.masterproject.view.main.MainActivity
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
        },Constants.mTimeLoadPage)
    }

}
