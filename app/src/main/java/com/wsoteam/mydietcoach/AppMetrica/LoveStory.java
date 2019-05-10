package com.wsoteam.mydietcoach.AppMetrica;

import android.app.Application;

import com.amplitude.api.Amplitude;
import com.wsoteam.mydietcoach.R;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class LoveStory extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "d0d5dffefe8b29a89279f15daf6d62b5").
                enableForegroundTracking(this);
    }
}
