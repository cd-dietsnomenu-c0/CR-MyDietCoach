package com.meal.planner.utils.water

import com.meal.planner.App
import com.meal.planner.common.db.entities.water.WaterRate
import com.meal.planner.utils.CustomDate
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