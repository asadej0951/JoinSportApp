package com.wewillapp.masterproject.utils.dialog

import android.app.Dialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.*
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.CustomImageFullscreenBinding
import com.wewillapp.masterproject.databinding.DialogAlertMessageBinding
import com.wewillapp.masterproject.databinding.DialogGalleryOrShootingBinding
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.imageManagement.ImageViewUtils


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