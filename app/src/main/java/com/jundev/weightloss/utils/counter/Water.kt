package com.jundev.weightloss.utils.counter

import com.jundev.weightloss.utils.PreferenceProvider

object Water {

    private val FEMALE_FACTOR = 31
    private val MALE_FACTOR = 35

    private val TRAINING_FACTOR = 0.1f
    private val HOT_FACTOR = 0.15f

    fun getWaterDailyRate(gender: Int, isTrainingOn: Boolean, isHotOn: Boolean, weight: Int): Int {
        return if (PreferenceProvider.getWaterRateChangedManual()!! != PreferenceProvider.EMPTY) {
            PreferenceProvider.getWaterRateChangedManual()!!
        } else {
            countRate(gender, isTrainingOn, isHotOn, weight)
        }

    }

    fun countRate(gender: Int, isTrainingOn: Boolean, isHotOn: Boolean, weight: Int): Int {
        var factor = if (gender == PreferenceProvider.SEX_TYPE_FEMALE) {
            FEMALE_FACTOR
        } else {
            MALE_FACTOR
        }

        var waterRate = weight * factor
        var trainingDiff = 0
        var hotDiff = 0

        if (isHotOn) {
            hotDiff = (waterRate * HOT_FACTOR).toInt()
        }

        if (isTrainingOn) {
            trainingDiff = (waterRate * TRAINING_FACTOR).toInt()
        }

        waterRate += trainingDiff + hotDiff
        return waterRate
    }
}