package com.wewillapp.masterproject.data

object Constants {
    const val TIME_CONNECT = 30L
    const val TIME_INTERVAL = 1000L * 600L
    const val TIME_INTERVAL_UNIT = 1000L

    // time splash screen
    const val mTimeLoadPage = 3000L

    // Permission
    const val ACTION_GET_CAMERA = 1
    const val ACTION_GET_GALLERY = 2
    const val REQUEST_PERMISSION_SETTING = 999

    const val MESSAGE_NO_INTERNET = "กรุณาตรวจสอบการเชื่อมต่ออินเตอร์เน็ต"

    // More Debug
    const val MODE_DEBUG = true

    private const val API_ENDPOINT_SSL = "http://"

    const val URL_PRO = API_ENDPOINT_SSL + "dev.easily.wewillapp.support/api/"

    const val URL_DEV = API_ENDPOINT_SSL + "dev.easily.wewillapp.support/api/"
}
