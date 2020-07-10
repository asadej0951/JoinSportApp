package com.wewillapp.masterproject.utils.facebook

import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.gson.Gson
import timber.log.Timber

class FacebookUtil {

    companion object {
        fun facebookToken(
            getType: FacebookGetType,
            loginButton: LoginButton,
            callbackManager: CallbackManager,
            callback: ((HashMap<String, Any>) -> Unit)
        ) {
            loginButton.setReadPermissions(listOf("public_profile", "email"))
            loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    val callbackData = HashMap<String, Any>()
                    callbackData["FacebookToken"] = loginResult.accessToken.token
                    callbackData["FacebookId"] = loginResult.accessToken.userId
                    if (getType == FacebookGetType.LOGIN) callback.invoke(callbackData)
                    else {
                        val request =
                            GraphRequest.newMeRequest(loginResult.accessToken) { data, response ->
                                val facebook =
                                    Gson().fromJson(data.toString(), Facebook::class.java)
                                callbackData["FacebookProfile"] = facebook.picture.data.url
                                callbackData["FacebookName"] = facebook.name
                                callbackData["FacebookEmail"] = facebook.email ?: ""
                                callback.invoke(callbackData)
                            }
                        val parameters = Bundle()
                        parameters.putString("fields", "id, picture.type(large), name, email")
                        request.parameters = parameters
                        request.executeAsync()
                    }
                }

                override fun onCancel() {
                    Timber.e("onCancel")
                }

                override fun onError(error: FacebookException) {
                    if (error is FacebookAuthorizationException && AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut()
                        loginButton.performClick()
                    }
                }
            })
        }
    }
}
