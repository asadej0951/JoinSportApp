package com.wewillapp.masterproject.utils.dialog

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.DialogAlertMessageBinding
import com.wewillapp.masterproject.databinding.DialogAlertMessageDefaultBinding
import com.wewillapp.masterproject.utils.extension.getDeviceMetrics
import com.wewillapp.masterproject.utils.rxBus.EventRxBus
import com.wewillapp.masterproject.vo.RxEvent

open class DialogFactory(open val fragmentActivity:FragmentActivity) {

    private val dialogMessage = getDialog()
    private val dialogMessageBtn = getDialog()

    fun dialogMessage(title: String, text: String?, clickCallback: ((Boolean) -> Unit)) {
        dialogMessage.setCanceledOnTouchOutside(false)
        val binding: DialogAlertMessageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(fragmentActivity),
                R.layout.dialog_alert_message, null, false
            )
        dialogMessage.setContentView(binding.root)
        dialogMessage.window?.attributes!!.width =
            (fragmentActivity.getDeviceMetrics().widthPixels * 0.8).toInt()

        binding.title = title
        binding.text = text

        binding.tvOkey.setOnClickListener {
            dialogMessage.dismiss()
            if (text == "401") {
                EventRxBus.onAddEventRxBus(RxEvent("log out"))
            } else {
                clickCallback.invoke(true)
            }
        }

        if (dialogMessage.isShowing)
            dialogMessage.dismiss()

        dialogMessage.show()
    }

    fun dialogMessage(
        message: String,
        messageBtn: String,
        iconDialog: Drawable,
        clickCallback: ((Boolean) -> Unit?)
    ) {

        dialogMessageBtn.setCanceledOnTouchOutside(false)
        dialogMessageBtn.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogMessageBtn.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val binding: DialogAlertMessageDefaultBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(fragmentActivity),
                R.layout.dialog_alert_message_default, null, false
            )
        dialogMessageBtn.setContentView(binding.root)
        dialogMessageBtn.window?.attributes!!.width =
            (fragmentActivity.getDeviceMetrics().widthPixels * 0.8).toInt()

        binding.text = message
        binding.messageBtn = messageBtn
        binding.ivLogoApp.setImageDrawable(iconDialog)

        binding.tvOkey.setOnClickListener {
            dialogMessageBtn.dismiss()
            clickCallback.invoke(true)
        }

        if (dialogMessageBtn.isShowing)
            dialogMessageBtn.dismiss()

        dialogMessageBtn.show()
    }

    fun getDialog(): Dialog = Dialog(fragmentActivity)

    fun getDialogFullScreen(): Dialog = Dialog(fragmentActivity, R.style.Dialog_FullScreen)
}