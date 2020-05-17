package com.wsoteam.mydietcoach;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.amplitude.api.Amplitude;
import com.google.android.gms.ads.MobileAds;
import com.wsoteam.mydietcoach.common.db.DietDatabase;

public class App extends Application {

    private static Context context;
    private DietDatabase db;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this);
        //BillingManager.INSTANCE.init(this);
        Amplitude.getInstance().initialize(this, "d0d5dffefe8b29a89279f15daf6d62b5").
                enableForegroundTracking(this);
        context = this;
        app = this;
        createNotificationChannel(getContext());

        db = Room.databaseBuilder(this, DietDatabase.class, "dietPlans")
                .allowMainThreadQueries()
                .build();
    }

    @SuppressLint("NewApi")
    public void createNotificationChannel(@NonNull Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            String channelId = "com.jundev.diets";
            String channelName = "com.jundev.diets";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification), att);
            channel.setLightColor(Color.parseColor("#4B8A08"));
            channel.setVibrationPattern(new long[]{0, 500});
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
