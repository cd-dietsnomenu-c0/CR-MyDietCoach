package com.calorie.dieta.utils.history

import com.calorie.dieta.common.GlobalHolder
import com.calorie.dieta.common.db.entities.HistoryDiet
import com.calorie.dieta.common.db.entities.UNKNOWN_PERIOD
import com.calorie.dieta.model.interactive.Diet
import com.calorie.dieta.utils.TimeConverter

object HistoryProvider {

    fun convertFloatToTwoNumbers(float: Float): Pair<Int, Int> {
        var firstNumber = float.toString().split(".")[0].toInt()
        var secondNumber = float.toString().split(".")[1].toInt()
        return Pair(firstNumber, secondNumber)
    }


    fun convertTwoNumbersToFloat(firstNumber: Int, secondNumber: Int): Float{
        return "${firstNumber}.${secondNumber}".toFloat()
    }

    fun addAdditionalProperties(historyDiet: HistoryDiet) : HistoryDiet{
        var diet = findDiet(historyDiet)
        historyDiet.imageUrl = diet!!.mainImage
        historyDiet.name = diet!!.title
        historyDiet.readableEnd = TimeConverter.fromMillisToString(historyDiet.endTime)

        if (historyDiet.startTime == 0L){
            historyDiet.readableStart = "-"
        }else{
            historyDiet.readableStart = TimeConverter.fromMillisToString(historyDiet.startTime)
        }

        if (historyDiet.startTime == 0L){
            historyDiet.readablePeriod = UNKNOWN_PERIOD
        }else{
            historyDiet.readablePeriod = TimeConverter.getPeriod(historyDiet.startTime, historyDiet.endTime) + 1
        }

        return historyDiet
    }

    private fun findDiet(historyDiet: HistoryDiet) : Diet{
        var diet = GlobalHolder.getGlobal().allDiets.dietList.find {
            it.index == historyDiet.dietNumber
        }
        return diet!!
    }
}