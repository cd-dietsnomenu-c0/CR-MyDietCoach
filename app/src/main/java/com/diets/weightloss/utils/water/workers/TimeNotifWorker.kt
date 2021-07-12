package com.diets.weightloss.utils.water.workers

import com.diets.weightloss.utils.PreferenceProvider

object TimeNotifWorker {

    private const val DELIMITERS = ":"

    private fun convertStringToInts(time: String): Pair<Int, Int> {
        return Pair<Int, Int>(time.split(DELIMITERS)[0].toInt(), time.split(DELIMITERS)[1].toInt())
    }

    fun getStartHour(): Int {
        return convertStringToInts(PreferenceProvider.startWaterNotifTime).first
    }

    fun getStartMinute(): Int {
        return convertStringToInts(PreferenceProvider.startWaterNotifTime).second
    }


    fun setStartTime(hour: Int, minute: Int): String {
        var readableTime = "${String.format("%02d", hour)}$DELIMITERS${String.format("%02d", minute)}"
        PreferenceProvider.startWaterNotifTime = readableTime
        return readableTime
    }




    fun getEndHour(): Int {
        return convertStringToInts(PreferenceProvider.endWaterNotifTime).first
    }

    fun getEndMinute(): Int {
        return convertStringToInts(PreferenceProvider.endWaterNotifTime).second
    }


    fun setEndTime(hour: Int, minute: Int): String {
        var readableTime = "${String.format("%02d", hour)}$DELIMITERS${String.format("%02d", minute)}"
        PreferenceProvider.endWaterNotifTime = readableTime
        return readableTime
    }


}