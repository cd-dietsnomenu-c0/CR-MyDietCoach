package com.wsoteam.mydietcoach;

import android.app.Application;
import android.content.Context;

import com.amplitude.api.Amplitude;
import com.wsoteam.mydietcoach.inapp.BillingManager;

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BillingManager.INSTANCE.init(this);
        Amplitude.getInstance().initialize(this, "d0d5dffefe8b29a89279f15daf6d62b5").
                enableForegroundTracking(this);
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
