package com.diets.weightloss.utils.notif.services

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.diets.weightloss.*
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.utils.water.workers.NotificationChecker
import java.util.*


class FCMService : FirebaseMessagingService() {


    override fun onMessageReceived(p0: RemoteMessage) {
        if (p0?.data != null && p0.data[Config.TYPE_KEY] != null && p0.data[Config.TYPE_KEY] == Config.WATER_TYPE) {
            if (isNeedShowWaterNotif()) {
                showWaterNotif()
            }
        } else if (p0?.data != null && p0.data[Config.TYPE_KEY] != null && p0.data[Config.TYPE_KEY] == Config.EAT_TYPE) {
            showEatTrackerNotif()
        }else{
            showReactNotif(p0)
        }
    }

    private fun isHasFood(): Boolean {
        return if (App.getInstance().db.dietDAO().getAll().isEmpty()) {
            false
        } else {
            DBHolder.set(App.getInstance().db.dietDAO().getAll()[0])
            !DBHolder.isCompletedYesterday()
        }
    }

    private fun showEatTrackerNotif() {
        if (isHasFood()) {
            Ampl.showEatNotif()
            PreferenceProvider.lastTimeWaterNotif = Calendar.getInstance().timeInMillis
            var intent = Intent(this, SplashActivity::class.java)
            intent.putExtra(Config.PUSH_TAG, Config.OPEN_FROM_PUSH)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


            var pendingIntent = PendingIntent
                    .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            var collapsedView = RemoteViews(packageName, R.layout.view_eat_notification)

            var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification)
            var notificationBuilder = NotificationCompat.Builder(this, getString(R.string.eat_channel_id))
                    .setSmallIcon(R.drawable.ic_restaurant)
                    .setLargeIcon(largeIcon)
                    .setAutoCancel(true)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.eat_notif))
                    .setVibrate(Const.VIBRO_PATTERN_EAT)
                    .setLights(Color.MAGENTA, 500, 1000)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(collapsedView)
            var notification = notificationBuilder.build()

            var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("com.jundev.diets",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }*/
            notificationManager.notify(0, notification)
        }
    }

    private fun isNeedShowWaterNotif(): Boolean {
        return PreferenceProvider.isTurnOnWaterNotifications
                && NotificationChecker.isRightTime()
                && NotificationChecker.isRightFrequent()
                && NotificationChecker.isRightDay()
                && NotificationChecker.checkLastIntakeStrategy()
                && NotificationChecker.checkNormaStrategy()

    }

    private fun showWaterNotif() {
        Ampl.showWaterNotif()
        PreferenceProvider.lastTimeWaterNotif = Calendar.getInstance().timeInMillis
        var intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


        var pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var collapsedView = RemoteViews(packageName, R.layout.view_water_notification)

        var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification)
        var notificationBuilder = NotificationCompat.Builder(this, getString(R.string.water_channel_id))
                .setSmallIcon(R.drawable.ic_water_drop_notif)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.drop_3))
                .setVibrate(Const.VIBRO_PATTERN_WATER)
                .setLights(Color.MAGENTA, 500, 1000)
                .setContentIntent(pendingIntent)
                .setCustomContentView(collapsedView)
        var notification = notificationBuilder.build()

        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("com.jundev.diets",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }*/
        notificationManager.notify(0, notification)
    }


    private fun showReactNotif(p0: RemoteMessage) {
        if (!PreferenceProvider.isHasPremium) {
            Ampl.showReactNotif()
            var intent = Intent(this, SplashActivity::class.java)
            intent.putExtra(Config.PUSH_TAG, Config.OPEN_FROM_PUSH)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            Ampl.recieveFCM()

            var pendingIntent = PendingIntent
                    .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            var collapsedView = RemoteViews(packageName, R.layout.view_notification)
            collapsedView.setTextViewText(R.id.tvNotificationTitle, p0.data["title"])

            var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification)
            var notificationBuilder = NotificationCompat.Builder(this, getString(R.string.react_channel_id))
                    .setSmallIcon(R.drawable.ic_small_notification)
                    .setLargeIcon(largeIcon)
                    .setAutoCancel(true)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification))
                    .setVibrate(Const.VIBRO_PATTERN_REACT)
                    .setLights(Color.MAGENTA, 500, 1000)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(collapsedView)
            var notification = notificationBuilder.build()
            var notificationTarget = NotificationTarget(this,
                    R.id.ivAvatarNotification, collapsedView,
                    notification, 0)
            Handler(Looper.getMainLooper()).post(Runnable {
                Glide.with(App.getContext()).asBitmap().load(p0.data["url"]).into(notificationTarget)
            })

            var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("com.jundev.diets",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }*/
            notificationManager.notify(0, notification)
        }
    }


}