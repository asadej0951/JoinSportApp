package com.wewillapp.masterproject.utils.facebook

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.gson.Gson
import com.wewillapp.masterproject.AppExecutors
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

class FacebookUtil {

    companion object {
        fun facebookToken(
            getType: FacebookGetType,
            loginButton: LoginButton,
            callbackManager: CallbackManager,
            Callback: ((HashMap<String, Any>) -> Unit)
        ) {
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email"))
            loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    val callbackData = HashMap<String, Any>()
                    callbackData["FacebookToken"] = loginResult.accessToken.token
                    callbackData["FacebookId"] = loginResult.accessToken.userId
                    if (getType == FacebookGetType.LOGIN) Callback.invoke(callbackData)
                    else {
                        val request = GraphRequest.newMeRequest(loginResult.accessToken) { data, response ->
                            val facebook = Gson().fromJson(data.toString(), Facebook::class.java)
                            callbackData["FacebookProfile"] = facebook.picture.data.url
                            callbackData["FacebookName"] = facebook.name
                            callbackData["FacebookEmail"] = facebook.email ?: ""
                            Callback.invoke(callbackData)
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id, picture.type(large), name, email")
                        request.parameters = parameters
                        request.executeAsync()
                    }
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {
                    if (error is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                            loginButton.performClick()
                        }
                    }
                }
            })
        }

        fun facebookImage(
            fragmentActivity: FragmentActivity,
            appExecutors: AppExecutors,
            src: String,
            Callback: ((File) -> Unit)
        ) {
            appExecutors.networkIO().execute {
                val url = URL(src)
                val input = url.openStream()
                val photo =
                    File(fragmentActivity.cacheDir, String.format("FacebookImage_%d.jpg", System.currentTimeMillis()))
                input.use { inputData ->
                    //The sdcard directory e.g. '/sdcard' can be used directly, or
                    //more safely abstracted with getExternalStorageDirectory()
                    val output = FileOutputStream(photo)
                    output.use { outputData ->
                        var read: Int? = null
                        val buffer = ByteArray(1024)

                        while ({ read = inputData.read(buffer); read }() != -1) {
                            outputData.write(buffer, 0, read!!)
                        }
                    }
                }
                Callback.invoke(photo)
            }
        }
    }
}