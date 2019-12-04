package com.onzon.delivery.user.utils

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentActivity
import android.widget.ImageView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.onzon.delivery.R
import com.onzon.delivery.user.data.Constants
import com.onzon.delivery.user.utils.dialog.DialogPresenter
import com.onzon.delivery.user.utils.imageManagement.ConvertUriToFile
import com.onzon.delivery.user.utils.imageManagement.ImageFilePath
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class CheckPermission @Inject constructor(var fragmentActivity: FragmentActivity) {

    @Inject
    lateinit var mConvertUriToFile: ConvertUriToFile

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    var mShooting: Uri? = null

    var mRealPath: String? = null

    fun checkPermissionCameraAndStorage() {
        Dexter.withActivity(fragmentActivity)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (!hasDeniedPermission(report)) {
                            mDialogPresenter.dialogSelectImage {
                                when (it) {
                                    1 -> showDialogGetCamera()
                                    2 -> showDialogGetGallery()
                                }
                            }
                        } else {
                            showSnackbar(fragmentActivity.resources.getString(R.string.permission_camera_storage))
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(list: List<PermissionRequest>, permissionToken: PermissionToken) {
                        permissionToken.continuePermissionRequest()
                    }

                    private fun hasDeniedPermission(report: MultiplePermissionsReport): Boolean {
                        val denyPermission = report.deniedPermissionResponses
                        return denyPermission != null && !denyPermission.isEmpty()
                    }
                }).check()
    }

    private fun showDialogGetCamera() {
        //Form Shooting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val photoShootingIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
            mShooting = fragmentActivity.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            photoShootingIntent.putExtra(MediaStore.EXTRA_OUTPUT, mShooting)
            fragmentActivity.startActivityForResult(photoShootingIntent, Constants.ACTION_GET_CAMERA)
        } else {
            val photoShootingIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            fragmentActivity.startActivityForResult(photoShootingIntent, Constants.ACTION_GET_CAMERA)
        }
    }

    private fun showDialogGetGallery() {
        //Form Gallery
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        val photoPickerIntent = Intent(Intent.ACTION_CHOOSER)
        photoPickerIntent.putExtra(Intent.EXTRA_INTENT, galleryIntent)
        photoPickerIntent.putExtra(Intent.EXTRA_TITLE, "Select Picture From: ")

        fragmentActivity.startActivityForResult(photoPickerIntent, Constants.ACTION_GET_GALLERY)
    }

    fun onSelectPicture(data: Intent?, imageView: ImageView) {
        if (data != null) {
            if (data.data != null) {
                mRealPath = ImageFilePath.getPath(fragmentActivity, data.data!!)
                imageView.setImageURI(Uri.parse(setRotation(mRealPath)))
            } else {
                if (data.extras != null) {
                    val extras = data.extras
                    val bitmap = extras?.get("data") as Bitmap
                    val uri = getImageUri(bitmap)
                    mRealPath = ImageFilePath.getPath(fragmentActivity, uri)
                    imageView.setImageURI(Uri.parse(setRotation(mRealPath)))
                } else {
                    mRealPath = ImageFilePath.getPath(fragmentActivity, mShooting!!)
                    imageView.setImageURI(Uri.parse(setRotation(mRealPath)))
                }
            }
        } else {
            if (mShooting != null) {
                mRealPath = ImageFilePath.getPath(fragmentActivity, mShooting!!)
                imageView.setImageURI(Uri.parse(setRotation(mRealPath)))
            }
        }
    }

    private fun getImageUri(inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(fragmentActivity.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getFile(): File? {
        return if (mRealPath != null) mConvertUriToFile.decodeFile(mRealPath!!) else null
    }

    private fun setRotation(mRealPath: String?): String {
        return mConvertUriToFile.decodeFile(mRealPath!!).toString()
    }

    private fun showSnackbar(alert: String) {
        Snackbar.make(fragmentActivity.window.decorView.rootView, alert, Snackbar.LENGTH_LONG)
                .setAction(fragmentActivity.resources.getString(R.string.permission_setting)) {
                    // Request the permission
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", fragmentActivity.packageName, null)
                    intent.data = uri
                    fragmentActivity.startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING)
                }.show()
    }

    fun checkPermissionPhone(tel: String, ClickCallback: ((Intent) -> Unit)) {
        Dexter.withActivity(fragmentActivity)
                .withPermissions(Manifest.permission.CALL_PHONE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (!hasDeniedPermission(report)) {
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel))
                            ClickCallback.invoke(intent)
                        } else {
                            showSnackbar(fragmentActivity.resources.getString(R.string.permission_call_phone))
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(list: List<PermissionRequest>, permissionToken: PermissionToken) {
                        permissionToken.continuePermissionRequest()
                    }

                    private fun hasDeniedPermission(report: MultiplePermissionsReport): Boolean {
                        val denyPermission = report.deniedPermissionResponses
                        return denyPermission != null && !denyPermission.isEmpty()
                    }
                }).check()
    }

    fun checkPermissionLocation(ClickCallback: ((Boolean) -> Unit)) {
        Dexter.withActivity(fragmentActivity)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (!hasDeniedPermission(report)) {
                            ClickCallback.invoke(true)
                        } else {
//                            ClickCallback.invoke(false)
                            showSnackbar(fragmentActivity.resources.getString(R.string.permission_location))
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>,
                                                                    token: PermissionToken) {
                        token.continuePermissionRequest()
                    }

                    private fun hasDeniedPermission(report: MultiplePermissionsReport): Boolean {
                        val denyPermission = report.deniedPermissionResponses
                        return denyPermission != null && !denyPermission.isEmpty()
                    }
                }).check()
    }

    fun checkPermissionReadAndWriteStorage(ClickCallback: ((Boolean) -> Unit)) {
        Dexter.withActivity(fragmentActivity)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (!hasDeniedPermission(report)) {
                            ClickCallback.invoke(true)
                        } else {
                            ClickCallback.invoke(false)
                            showSnackbar(fragmentActivity.resources.getString(R.string.permission_location))
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>,
                                                                    token: PermissionToken) {
                        token.continuePermissionRequest()
                    }

                    private fun hasDeniedPermission(report: MultiplePermissionsReport): Boolean {
                        val denyPermission = report.deniedPermissionResponses
                        return denyPermission != null && !denyPermission.isEmpty()
                    }
                }).check()
    }


    private fun getDialog(): Dialog = Dialog(fragmentActivity)
}