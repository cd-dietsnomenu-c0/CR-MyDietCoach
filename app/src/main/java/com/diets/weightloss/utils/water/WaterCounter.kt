package com.diets.weightloss.utils.water

import android.util.Log
import com.diets.weightloss.common.db.entities.water.WaterIntake
import com.diets.weightloss.common.db.entities.water.WaterRate
import com.diets.weightloss.model.water.WaterMarathon
import com.diets.weightloss.utils.CustomDate
import com.diets.weightloss.utils.PreferenceProvider
import java.util.*
import kotlin.collections.ArrayList

object WaterCounter {

    private val FEMALE_FACTOR = 31
    private val MALE_FACTOR = 35

    private val TRAINING_FACTOR = 0.1f
    private val HOT_FACTOR = 0.15f

    private val ONE_DAY_MILLIS = 86400000L

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

    fun mergeIntakesIntoDays(listIntakes: ArrayList<WaterIntake>): ArrayList<WaterIntake> {
        var daysList = arrayListOf<WaterIntake>()


        //set in all intakes clear time in millis
        for (i in listIntakes.indices) {
            var time = CustomDate.getClearTime(listIntakes[i].id)
            var intake = listIntakes[i]
            intake.id = time
            listIntakes[i] = intake
        }

        //merge all intakes in whole days
        for (i in listIntakes.indices) {
            if (!isContainsItem(daysList, listIntakes[i])) {
                var counter = i + 1
                var dayIntake = listIntakes[i].copy()
                while (counter < listIntakes.size && dayIntake.id == listIntakes[counter].id) {
                    dayIntake.clearCapacity += listIntakes[counter].clearCapacity
                    counter++
                }
                daysList.add(dayIntake)
            }
        }

        return daysList
    }

    fun getMarathonDays(listIntakes: ArrayList<WaterIntake>, listRates: ArrayList<WaterRate>): Int {
        var daysIntakes = mergeIntakesIntoDays(listIntakes)

        daysIntakes.reverse()
        listRates.reverse()

        var ratesForEveryDay = getRatesForEveryDay(daysIntakes, listRates)


        var days = 0

        for (i in daysIntakes.indices) {
            if (daysIntakes[i].clearCapacity >= ratesForEveryDay[i].rate) {
                days++
            } else {
                if (i != 0) {
                    break
                }
            }
        }

        return days
    }

    fun getSortedMarathons(listIntakes: ArrayList<WaterIntake>, listRates: ArrayList<WaterRate>): List<WaterMarathon> {
        var unsortedMarathons = getMarathons(listIntakes, listRates)

        for (i in unsortedMarathons.indices){
            var daysAmount = ((unsortedMarathons[i].end - unsortedMarathons[i].start) / ONE_DAY_MILLIS).toInt() + 1
            unsortedMarathons[i].duration = daysAmount
        }

        var sortedMarathon = unsortedMarathons.sortedBy { it.duration }

        for (i in sortedMarathon){
            Log.e("LOL", i.duration.toString())
        }
        sortedMarathon.reversed()
        return sortedMarathon
    }

    private fun getMarathons(listIntakes: ArrayList<WaterIntake>, listRates: ArrayList<WaterRate>): ArrayList<WaterMarathon> {
        var daysIntakes = mergeIntakesIntoDays(listIntakes)


        var ratesForEveryDay = getRatesForEveryDay(daysIntakes, listRates)
        var marathons = arrayListOf<WaterMarathon>()

        var isAddNow = false
        var isNeedStopNext = false
        var startItem = 0
        var capacity = 0
        var daysInMarathon = 0

        for (i in daysIntakes.indices) {
            //
            if (daysIntakes[i].clearCapacity >= ratesForEveryDay[i].rate && !isNeedStopNext) {
                if (!isAddNow) {
                    isAddNow = true
                    startItem = i
                }
                capacity += daysIntakes[i].clearCapacity
                daysInMarathon++
                if (i == daysIntakes.size - 1) {
                    if (daysIntakes.size == 1) {
                        marathons.add(WaterMarathon(daysIntakes[startItem].id, daysIntakes[startItem].id, capacity, 0))
                    } else {
                        marathons.add(WaterMarathon(daysIntakes[startItem].id, daysIntakes[i].id, capacity, 0))
                    }
                } else {
                    if (daysIntakes[i + 1].id - daysIntakes[i].id > ONE_DAY_MILLIS) {
                        isNeedStopNext = true
                    }
                }
            } else {
                if (isAddNow) {
                    if (daysInMarathon > 1) {
                        marathons.add(WaterMarathon(daysIntakes[startItem].id, daysIntakes[i - 1].id, capacity, 0))
                    }
                    startItem = 0
                    capacity = 0
                    isAddNow = false
                    daysInMarathon = 0

                    if (isNeedStopNext) {
                        isNeedStopNext = false
                    }
                }
            }
        }

        return marathons
    }

    private fun getRatesForEveryDay(daysIntakes: ArrayList<WaterIntake>, listRates: ArrayList<WaterRate>): ArrayList<WaterRate> {
        var ratesForEveryDay = arrayListOf<WaterRate>()
        for (i in daysIntakes.indices) {
            for (j in listRates.indices) {
                if (daysIntakes[i].id >= listRates[j].timestamp) {
                    ratesForEveryDay.add(listRates[j].copy())
                    break
                }
            }
        }
        return ratesForEveryDay
    }

    private fun isContainsItem(daysList: ArrayList<WaterIntake>, waterIntake: WaterIntake): Boolean {
        var isContains = false
        for (i in daysList.indices) {
            if (daysList[i].id == waterIntake.id) {
                isContains = true
                break
            }
        }
        return isContains
    }
}