package com.calorie.dieta.utils.water

import com.calorie.dieta.App
import com.calorie.dieta.common.db.entities.water.WaterRate
import com.calorie.dieta.utils.CustomDate
import java.util.*

object WaterRateProvider {

    fun addNewRate(newRate : Int){
        var timeInMillis = CustomDate.getClearTime(Calendar.getInstance().timeInMillis)
        App.getInstance().db.dietDAO().addNewRate(WaterRate(timeInMillis, newRate))
    }

    fun addNewRateCustomTime(newRate : Int, time : Long){
        var timeInMillis = CustomDate.getClearTime(time)
        App.getInstance().db.dietDAO().addNewRate(WaterRate(timeInMillis, newRate))
    }
}