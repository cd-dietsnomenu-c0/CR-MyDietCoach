package com.diets.weightloss.utils.history

import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.model.interactive.Diet
import com.diets.weightloss.utils.TimeConverter

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
        historyDiet.readableStart = TimeConverter.fromMillisToString(historyDiet.startTime)
        historyDiet.readableEnd = TimeConverter.fromMillisToString(historyDiet.endTime)
        historyDiet.readablePeriod = TimeConverter.getPeriod(historyDiet.startTime, historyDiet.endTime) + 1
        return historyDiet
    }

    private fun findDiet(historyDiet: HistoryDiet) : Diet{
        var diet = GlobalHolder.getGlobal().allDiets.dietList.find {
            it.index == historyDiet.dietNumber
        }
        return diet!!
    }
}