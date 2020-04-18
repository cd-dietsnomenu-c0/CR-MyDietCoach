package com.wsoteam.mydietcoach.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wsoteam.mydietcoach.BuildConfig
import com.wsoteam.mydietcoach.MainActivity
import com.wsoteam.mydietcoach.R


class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        var pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification)
        var notificationBuilder = NotificationCompat.Builder(this, "com.jundev.diets")
                .setSmallIcon(R.drawable.ic_small_notification)
                .setLargeIcon(largeIcon)
                .setContentTitle(p0.notification?.title)
                .setContentText(p0.notification?.body)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification))
                .setAutoCancel(true)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification))
                .setVibrate(longArrayOf(0, 500))
                .setLights(Color.MAGENTA, 500, 1000)
                .setContentIntent(pendingIntent)

        var notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("com.jundev.diets",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }*/
        notificationManager.notify(0, notificationBuilder.build())
    }


}