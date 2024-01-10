package com.meal.planner.utils.water.workers

import com.meal.planner.App
import com.meal.planner.R
import com.meal.planner.utils.PreferenceProvider

object DaysWorkers {

    private const val DELIMITER = "-"
    private const val TURN_ON = "1"
    private const val TURN_OFF = "0"

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
        return readableText
    }


    fun getDaysStates(pattern : String) : List<Boolean>{
        var rawList = pattern.split(DELIMITER)
        var listStates = arrayListOf<Boolean>()

        for (i in rawList.indices){
            listStates.add(rawList[i] == TURN_ON)
        }
        return listStates
    }

    fun saveDaysStates(states : List<Boolean>){
        var pattern = ""

        for (i in states.indices){
            pattern = if (states[i]){
                "$pattern$TURN_ON"
            }else{
                "$pattern$TURN_OFF"
            }

            if (i != states.size - 1){
                pattern = "$pattern$DELIMITER"
            }
        }

        PreferenceProvider.daysNotificationsType = pattern
    }
}