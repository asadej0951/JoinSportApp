package com.service.easily.stores.utility.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.view.main.MainActivity
import me.leolin.shortcutbadger.ShortcutBadger

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage!!.notification
        val data = remoteMessage.data

        sendNotification(data,notification)
    }

    @SuppressLint("WrongConstant")
    private fun sendNotification(
        data: MutableMap<String, String>,
        notification: RemoteMessage.Notification?) {
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        for (i in data.keys)
            Log.i("checkNotification","$i == ${data[i]}")

        ShortcutBadger.applyCount(applicationContext, data["badge"]?.toInt()!!)

        val intentRefresh = Intent("RefreshActivity")
        intentRefresh.putExtra("extra_broadcast", "NOTIFICATION")
        intentRefresh.putExtra("badge", data["badge"].toString())
        sendBroadcast(intentRefresh)

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification!!.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.title)
                .setLargeIcon(icon)
                .setColor(Color.BLACK)
                .setLights(Color.BLACK, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channel = NotificationChannel(
                    "default", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            )

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build()

            channel.apply {
                description = "channel description"
                setShowBadge(true)
                enableLights(true)
                lightColor = Color.BLACK
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
                setSound(defaultSound, audioAttributes)
            }

            notificationBuilder.setChannelId("default")
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}