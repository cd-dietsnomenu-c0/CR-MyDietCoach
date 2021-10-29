package com.diets.weightloss.utils.history

object HistoryFormatter {

    fun convertFloatToTwoNumbers(float: Float): Pair<Int, Int> {
        var firstNumber = float.toString().split(".")[0].toInt()
        var secondNumber = float.toString().split(".")[1].toInt()
        return Pair(firstNumber, secondNumber)
    }


    fun convertTwoNumbersToFloat(firstNumber: Int, secondNumber: Int): Float{
        return "${firstNumber}.${secondNumber}".toFloat()
    }
}