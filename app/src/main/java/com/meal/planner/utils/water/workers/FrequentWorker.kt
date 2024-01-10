package com.meal.planner.utils.water.workers

import com.meal.planner.App
import com.meal.planner.R
import com.meal.planner.utils.PreferenceProvider

object FrequentWorker {

    fun getReadableFrequent(): String {
        return App.getContext().resources.getStringArray(R.array.frequent_types)[PreferenceProvider.frequentNotificationsType]
    }


}