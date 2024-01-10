package com.meal.planner.utils.water.workers

import com.meal.planner.utils.PreferenceProvider
import java.util.*

object NotificationChecker {

    private const val MIN_30 = 1800000L
    private const val MIN_60 = 3600000L
    private const val MIN_90 = 5400000L
    private const val MIN_120 = 7200000L
    private const val MIN_150 = 9000000L
    private const val MIN_180 = 10800000L

    private const val MIN_15 = 900000L


    private const val MONDAY_INDEX = 0
    private const val TUESDAY_INDEX = 1
    private const val WEDNESDAY_INDEX = 2
    private const val THURSDAY_INDEX = 3
    private const val FRIDAY_INDEX = 4
    private const val SATURDAY_INDEX = 5
    private const val SUNDAY_INDEX = 6


    fun isRightFrequent() : Boolean{
        var listTimes = arrayListOf(MIN_30, MIN_60, MIN_90, MIN_120, MIN_150, MIN_180)
        var currentTime = Calendar.getInstance().timeInMillis
        var lastTime = PreferenceProvider.lastTimeWaterNotif
        return currentTime - lastTime > listTimes[PreferenceProvider.frequentNotificationsType]
    }

    fun isRightTime() : Boolean{
        var cal = Calendar.getInstance()
        var currentTime = cal.timeInMillis
        cal.set(Calendar.HOUR_OF_DAY, TimeNotifWorker.getStartHour())
        cal.set(Calendar.MINUTE, TimeNotifWorker.getStartMinute())
        var startTime = cal.timeInMillis

        cal.set(Calendar.HOUR_OF_DAY, TimeNotifWorker.getEndHour())
        cal.set(Calendar.MINUTE, TimeNotifWorker.getEndMinute())
        var endTime = cal.timeInMillis

        return currentTime in startTime..endTime
    }

    fun isRightDay() : Boolean{
        var states = DaysWorkers.getDaysStates(PreferenceProvider.daysNotificationsType)
        var dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        return when(dayOfWeek){
            Calendar.MONDAY -> states[MONDAY_INDEX]
            Calendar.TUESDAY -> states[TUESDAY_INDEX]
            Calendar.WEDNESDAY -> states[WEDNESDAY_INDEX]
            Calendar.THURSDAY -> states[THURSDAY_INDEX]
            Calendar.FRIDAY -> states[FRIDAY_INDEX]
            Calendar.SATURDAY -> states[SATURDAY_INDEX]
            Calendar.SUNDAY -> states[SUNDAY_INDEX]
            else -> false
        }
    }

    fun checkLastIntakeStrategy() : Boolean{
        return if (PreferenceProvider.isTurnOnRecentlyWater){
            true
        }else{
            Calendar.getInstance().timeInMillis - PreferenceProvider.lastTimeWaterIntake >= MIN_15
        }
    }

    fun checkNormaStrategy() : Boolean{
        return if (PreferenceProvider.isTurnOnAfterWaterNorm){
            true
        }else{
            Calendar.getInstance().get(Calendar.DAY_OF_YEAR) != PreferenceProvider.lastNormWaterDay
        }
    }
}