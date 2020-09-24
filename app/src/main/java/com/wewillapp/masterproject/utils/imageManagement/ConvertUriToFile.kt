package com.wewillapp.masterproject.utils.imageManagement

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class ConvertUriToFile constructor(private var fragmentActivity: Context) {

    fun decodeFile(filePath: String): File? {
        // Decode image size
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, o)

        // The new size we want to scale to
        val requestSize = 1024

        // Find the correct scale value. It should be the power of 2.
        var widthTmp = o.outWidth
        var heightTmp = o.outHeight
        var scale = 1
        while (true) {
            if (widthTmp < requestSize && heightTmp < requestSize)
                break
            widthTmp /= 2
            heightTmp /= 2
            scale *= 2
        }

        // Decode with inSampleSize
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        val b1 = BitmapFactory.decodeFile(filePath, o2)
        b1?.let {
            val b =
                ExifUtils.rotateBitmap(filePath, it)
            return persistImage(b, File(filePath).name)
        }
        return null
    }

    fun decodeMultipleFile(filePath: String): File {
        // Decode image size
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, o)

        // The new size we want to scale to
        val requestSize = 2048

        // Find the correct scale value. It should be the power of 2.
        var widthTmp = o.outWidth
        var heightTmp = o.outHeight
        var scale = 1
        while (true) {
            if (widthTmp < requestSize && heightTmp < requestSize)
                break
            widthTmp /= 2
            heightTmp /= 2
            scale *= 2
        }

        // Decode with inSampleSize
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        val b1 = BitmapFactory.decodeFile(filePath, o2)
        val b = ExifUtils.rotateBitmap(filePath, b1)

        return persistImage(b, File(filePath).name)
    }

    private fun persistImage(bitmap: Bitmap, name: String): File {
        val filesDir = fragmentActivity.filesDir
        val image = File(filesDir, name)

        val os: OutputStream
        try {
            os = FileOutputStream(image)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            return image
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }
        return image
    }
}
