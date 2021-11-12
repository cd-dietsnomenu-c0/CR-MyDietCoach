package com.diets.weightloss;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.amplitude.api.Amplitude;
import com.diets.weightloss.common.db.DietDatabase;
import com.diets.weightloss.common.db.migrations.Migrations;
import com.diets.weightloss.utils.inapp.SubscriptionProvider;
import com.google.android.gms.ads.MobileAds;
import com.userexperior.UserExperior;

public class App extends MultiDexApplication {

    private static Context context;
    private DietDatabase db;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this);
        SubscriptionProvider.INSTANCE.init(this);
        Amplitude.getInstance().initialize(this, "d0d5dffefe8b29a89279f15daf6d62b5").
                enableForegroundTracking(this);
        context = this;
        app = this;
        createNotificationChannel(getContext());

        db = Room.databaseBuilder(this, DietDatabase.class, "dietPlans")
                .addMigrations(Migrations.INSTANCE.getMigration_1_2(), Migrations.INSTANCE.getMigration_2_3(),
                        Migrations.INSTANCE.getMigration_3_4(), Migrations.INSTANCE.getMigration_4_5())
                .allowMainThreadQueries()
                .build();

        UserExperior.startRecording(
                getApplicationContext(),
                getString(R.string.release_user_expirior_id)
        );

        //Bugsee.launch(this, "5bdc0639-f870-4536-8038-8977c685cfc7");
    }

    @SuppressLint("NewApi")
    public void createNotificationChannel(@NonNull Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            String channelId = getString(R.string.react_channel_id);
            String channelName = getString(R.string.react_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification), att);
            channel.setLightColor(Color.parseColor("#4B8A08"));
            channel.setVibrationPattern(Const.INSTANCE.getVIBRO_PATTERN_REACT());
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            String channelId = getString(R.string.water_channel_id);
            String channelName = getString(R.string.water_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.drop_3), att);
            channel.setLightColor(Color.parseColor("#4B8A08"));
            channel.setVibrationPattern(Const.INSTANCE.getVIBRO_PATTERN_WATER());
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            String channelId = getString(R.string.eat_channel_id);
            String channelName = getString(R.string.eat_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.eat_notif), att);
            channel.setLightColor(Color.parseColor("#4B8A08"));
            channel.setVibrationPattern(Const.INSTANCE.getVIBRO_PATTERN_EAT());
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return app;
    }

    public DietDatabase getDB() {
        return db;
    }
}
