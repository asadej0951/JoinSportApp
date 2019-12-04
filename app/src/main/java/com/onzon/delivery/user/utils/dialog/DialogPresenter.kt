package com.onzon.delivery.user.utils.dialog

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.onzon.delivery.R
import com.onzon.delivery.databinding.CustomImageFullscreenBinding
import com.onzon.delivery.databinding.DialogAlertMessageBinding
import com.onzon.delivery.databinding.DialogGalleryOrShootingBinding
import com.onzon.delivery.databinding.DialogMessageNoTitleBinding
import com.onzon.delivery.user.utils.Utils
import com.onzon.delivery.user.utils.imageManagement.ImageViewUtils
import javax.inject.Inject


class DialogPresenter @Inject constructor(private var fragmentActivity: FragmentActivity) {
    @Inject
    lateinit var mUtils: Utils

    fun dialogAlertMessage(title: String, text: String?, ClickCallback: ((Boolean) -> Unit)) {
            val dialog = getDialog()
            dialog.setCanceledOnTouchOutside(false)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val binding: DialogAlertMessageBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(fragmentActivity),
                    R.layout.dialog_alert_message, null, false
                )
            dialog.setContentView(binding.root)

            binding.title = title
            binding.text = text

            binding.tvOkey.setOnClickListener {
                dialog.dismiss()
                ClickCallback.invoke(true)
            }

            dialog.show()
    }


    @SuppressLint("RtlHardcoded")
    fun dialogAlertMessageNotitle(message: String?, ClickCallback: ((Boolean) -> Unit)) {
        val dialog = getDialog()
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: DialogMessageNoTitleBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(fragmentActivity),
                R.layout.dialog_message_no_title, null, false
            )
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.window?.attributes!!.width =
            (getDeviceMetrics(fragmentActivity).widthPixels * 0.8).toInt()
        binding.message = message

        binding.tvOkey.setOnClickListener {
            dialog.dismiss()
            ClickCallback.invoke(true)
        }

        dialog.show()
    }

    private fun getDeviceMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getMetrics(metrics)
        return metrics
    }

    fun showAlertDialogFullScreen(context: Context, mImageUrl: String) {
        val mImageViewUtils = ImageViewUtils()
        val dialog = getDialog()
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val binding: CustomImageFullscreenBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(fragmentActivity),
                R.layout.custom_image_fullscreen, null, false
            )
        dialog.setContentView(binding.root)

        binding.ivClose.setOnClickListener {
            dialog.cancel()
        }

        mImageViewUtils.setImageView(context, mImageUrl, binding.ivShow)

        dialog.setCancelable(true)
        dialog.show()

    }

    fun dialogSelectImage(ClickCallback: ((Int) -> Unit)) {
        val dialog = getDialog()
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(Gravity.BOTTOM)
        val binding: DialogGalleryOrShootingBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(fragmentActivity),
                R.layout.dialog_gallery_or_shooting, null, false
            )
        dialog.setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            ClickCallback.invoke(1)
            dialog.dismiss()
        }

        binding.btnGallery.setOnClickListener {
            ClickCallback.invoke(2)
            dialog.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun getDialog(): Dialog = Dialog(fragmentActivity)

    fun getDialogFullScreen(): Dialog = Dialog(fragmentActivity, R.style.Dialog_FullScreen)
}