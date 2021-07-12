package com.diets.weightloss.utils.water.workers

import com.diets.weightloss.App
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider

object FrequentWorker {

    fun getReadableFrequent(): String {
        return App.getContext().resources.getStringArray(R.array.frequent_types)[PreferenceProvider.frequentNotificationsType]
    }


}