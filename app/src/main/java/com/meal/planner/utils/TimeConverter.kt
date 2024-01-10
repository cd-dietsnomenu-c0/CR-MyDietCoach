package com.meal.planner.utils

import java.util.*

object TimeConverter {

    private const val ONE_DAY_MILLIS = 86_400_000L

    fun fromMillisToString(timeInMillis: Long): String {
        var cal = Calendar.getInstance()
        cal.timeInMillis = timeInMillis
        return "${String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))}.${String.format("%02d", cal.get(Calendar.MONTH) + 1)}.${cal.get(Calendar.YEAR)}"
    }

    fun getPeriod(startMillis: Long, endMillis: Long): Int {
        return ((endMillis - startMillis) / ONE_DAY_MILLIS).toInt()
    }
}