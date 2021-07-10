package com.diets.weightloss.utils.workers

import com.diets.weightloss.App
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider

object DaysWorkers {

    private const val DELIMITER = "-"
    private const val TURN_ON = "1"

    fun getReadableDays() : String{
        var listStates = PreferenceProvider.daysNotificationsType.split(DELIMITER)
        var listRedableDays = App.getContext().resources.getStringArray(R.array.days_type)
        var listOnReabableDays = arrayListOf<String>()

        for (i in listStates.indices){
            if (listStates[i] == TURN_ON){
                listOnReabableDays.add(listRedableDays[i])
            }
        }

        if (listOnReabableDays.size == 7) return App.getContext().getString(R.string.all_days_state)
        if (listOnReabableDays.size == 0) return App.getContext().getString(R.string.empty_days_state)

        var readableText = ""

        for (i in listOnReabableDays.indices){
            readableText = "$readableText${listOnReabableDays[i]}"
            if (i != listOnReabableDays.size - 1){
                readableText = "$readableText, "
            }

        }

    }
}