package com.wsoteam.highschoollovestory.AppMetrica;

import android.app.Application;

import com.wsoteam.highschoollovestory.R;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class LoveStory extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация AppMetrica SDK
        YandexMetricaConfig.Builder configBuilder = YandexMetricaConfig.newConfigBuilder(getResources().getString(R.string.YM));
        YandexMetrica.activate(getApplicationContext(), configBuilder.build());
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);
    }
}
