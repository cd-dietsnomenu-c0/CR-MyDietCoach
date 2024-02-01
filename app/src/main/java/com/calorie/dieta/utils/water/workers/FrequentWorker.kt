package com.calorie.dieta.utils.water.workers

import com.calorie.dieta.App
import com.calorie.dieta.R
import com.calorie.dieta.utils.PreferenceProvider

object FrequentWorker {

    fun getReadableFrequent(): String {
        return App.getContext().resources.getStringArray(R.array.frequent_types)[PreferenceProvider.frequentNotificationsType]
    }


}