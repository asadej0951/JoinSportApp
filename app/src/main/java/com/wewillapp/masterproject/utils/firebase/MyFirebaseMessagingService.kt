package com.wewillapp.masterproject.utils.firebase

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
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.view.main.MainActivity
import me.leolin.shortcutbadger.ShortcutBadger

@SuppressLint("Registered")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage.notification
        val data = remoteMessage.data

        sendNotification(data, notification)
    }

    @SuppressLint("WrongConstant")
    private fun sendNotification(
        data: MutableMap<String, String>,
        notification: RemoteMessage.Notification?
    ) {
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationID = System.currentTimeMillis()
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationID.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

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
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notification.body))


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(notificationBuilder, notificationManager)
        } else
            onCheckSetting(notificationBuilder)

        notificationManager.notify(0, notificationBuilder.build())
    }

    @SuppressLint("NewApi", "WrongConstant")
    private fun createChannelId(
        notificationBuilder: NotificationCompat.Builder,
        notificationManager: NotificationManager
    ) {
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channel =
            NotificationChannel("default", "channel_name", NotificationManager.IMPORTANCE_DEFAULT)

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
            importance = NotificationManager.IMPORTANCE_MAX
        }

        notificationBuilder.priority = 2
        notificationBuilder.setChannelId("default")
        notificationManager.createNotificationChannel(channel)
    }

    private fun onCheckSetting(notification: NotificationCompat.Builder) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val defaultVibration = longArrayOf(500, 1000, 500, 500, 1000)
        notification.setSound(defaultSoundUri).setVibrate(defaultVibration)
    }
}