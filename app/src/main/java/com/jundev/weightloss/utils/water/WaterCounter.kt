package com.jundev.weightloss.utils.water

import com.jundev.weightloss.utils.PreferenceProvider

object WaterCounter {

    private val FEMALE_FACTOR = 31
    private val MALE_FACTOR = 35

    private val TRAINING_FACTOR = 0.1f
    private val HOT_FACTOR = 0.15f

    fun getWaterDailyRate(gender: Int, isTrainingOn: Boolean, isHotOn: Boolean, weight: Int, isNeedConsiderFactors: Boolean): Int {
        return if (PreferenceProvider.getWaterRateChangedManual()!! != PreferenceProvider.EMPTY) {
            if (!isNeedConsiderFactors) {
                PreferenceProvider.getWaterRateChangedManual()!!
            } else {
                if (!isHotOn && !isTrainingOn) {
                    PreferenceProvider.getWaterRateChangedManual()!!
                } else {
                    countWithFactors(PreferenceProvider.getWaterRateChangedManual()!!, isHotOn, isTrainingOn)
                }
            }
        } else {
            countRate(gender, isTrainingOn, isHotOn, weight)
        }

    }

    private fun countWithFactors(waterRateChangedManual: Int, isHotOn: Boolean, isTrainingOn: Boolean): Int {
        var trainingDiff = 0
        var hotDiff = 0

        if (isHotOn) {
            hotDiff = (waterRateChangedManual * HOT_FACTOR).toInt()
        }

        if (isTrainingOn) {
            trainingDiff = (waterRateChangedManual * TRAINING_FACTOR).toInt()
        }

        return waterRateChangedManual + trainingDiff + hotDiff
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