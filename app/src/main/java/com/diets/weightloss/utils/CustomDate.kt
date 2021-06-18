package com.diets.weightloss.utils

import java.util.*

object CustomDate {

    const val MILLIS_IN_DAY = 86400000L

    fun getClearTime(timeInMillis : Long): Long {
        var cal = Calendar.getInstance()
        cal.timeInMillis = timeInMillis
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}