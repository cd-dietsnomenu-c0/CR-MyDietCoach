package com.diets.weightloss.utils.water

import com.diets.weightloss.App
import com.diets.weightloss.common.db.entities.water.WaterRate
import java.util.*

object WaterRateProvider {

    fun addNewRate(newRate : Int){
        var cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        var timeInMillis = cal.timeInMillis

        App.getInstance().db.dietDAO().addNewRate(WaterRate(timeInMillis, newRate))

    }
}