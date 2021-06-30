package com.diets.weightloss.utils.water

import com.diets.weightloss.App
import com.diets.weightloss.common.db.entities.water.WaterRate
import com.diets.weightloss.utils.CustomDate
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