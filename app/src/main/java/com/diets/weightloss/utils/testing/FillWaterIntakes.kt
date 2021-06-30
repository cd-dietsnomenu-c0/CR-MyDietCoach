package com.diets.weightloss.utils.testing

import com.diets.weightloss.App
import com.diets.weightloss.common.db.entities.water.WaterIntake
import com.diets.weightloss.common.db.entities.water.WaterRate
import com.diets.weightloss.utils.water.WaterRateProvider
import java.util.*

object FillWaterIntakes {

    fun fillDB() {
        var list = arrayListOf<WaterIntake>()
        //11
        list.add(WaterIntake(getTimeMillis(11) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(11) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(11) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(11) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(11) + 5, 0, 50, 20))
        //12
        list.add(WaterIntake(getTimeMillis(12) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(12) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(12) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(12) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(12) + 5, 0, 50, 20))
        //13
        list.add(WaterIntake(getTimeMillis(13) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(13) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(13) + 3, 0, 50, 20))
        //14
        list.add(WaterIntake(getTimeMillis(14) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(14) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(14) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(14) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(14) + 5, 0, 50, 20))
        //16
        list.add(WaterIntake(getTimeMillis(16) + 1, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(16) + 2, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(16) + 3, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(16) + 4, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(16) + 5, 0, 50, 40))
        //18
        list.add(WaterIntake(getTimeMillis(18) + 1, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(18) + 2, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(18) + 3, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(18) + 4, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(18) + 5, 0, 50, 40))
        //19
        list.add(WaterIntake(getTimeMillis(19) + 1, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(19) + 2, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(19) + 3, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(19) + 4, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(19) + 5, 0, 50, 40))
        //20
        list.add(WaterIntake(getTimeMillis(20) + 1, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(20) + 2, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(20) + 3, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(20) + 4, 0, 50, 40))
        list.add(WaterIntake(getTimeMillis(20) + 5, 0, 50, 40))
        //23
        list.add(WaterIntake(getTimeMillis(23) + 1, 0, 50, 300))
        //24
        list.add(WaterIntake(getTimeMillis(24) + 1, 0, 50, 90))
        //25
        list.add(WaterIntake(getTimeMillis(25) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(25) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(25) + 3, 0, 50, 10))
        //26
        list.add(WaterIntake(getTimeMillis(26) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(26) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(26) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(26) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(26) + 5, 0, 50, 20))
        //27
        list.add(WaterIntake(getTimeMillis(27) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(27) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(27) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(27) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(27) + 5, 0, 50, 20))
        //28
        list.add(WaterIntake(getTimeMillis(28) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(28) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(28) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(28) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(28) + 5, 0, 50, 20))
        //29
        list.add(WaterIntake(getTimeMillis(29) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(29) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(29) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(29) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(29) + 5, 0, 50, 20))
        //30
        list.add(WaterIntake(getTimeMillis(30) + 1, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(30) + 2, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(30) + 3, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(30) + 4, 0, 50, 20))
        list.add(WaterIntake(getTimeMillis(30) + 5, 0, 50, 20))

        for (intake in list) {
            App
                    .getInstance()
                    .db
                    .dietDAO()
                    .addWater(intake)
        }


        WaterRateProvider.addNewRateCustomTime(100, getTimeMillis(10))
        WaterRateProvider.addNewRateCustomTime(200, getTimeMillis(15))
        WaterRateProvider.addNewRateCustomTime(300, getTimeMillis(21))
        WaterRateProvider.addNewRateCustomTime(100, getTimeMillis(25))




    }

    private fun getTimeMillis(dayOfMonth: Int): Long {
        var cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return cal.timeInMillis
    }
}