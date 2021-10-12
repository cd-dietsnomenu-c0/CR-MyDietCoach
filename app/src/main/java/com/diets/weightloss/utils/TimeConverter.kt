package com.diets.weightloss.utils

import java.util.*

object TimeConverter {

    fun fromMillisToString(timeInMillis: Long): String {
        var cal = Calendar.getInstance()
        cal.timeInMillis = timeInMillis
        return "${String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))}.${String.format("%02d", cal.get(Calendar.MONTH) + 1)}.${cal.get(Calendar.YEAR)}"
    }
}