package com.meal.planner.presentation.water

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.meal.planner.App
import com.meal.planner.R
import com.meal.planner.common.db.entities.water.DrinksCapacities
import com.meal.planner.common.db.entities.water.WaterIntake
import com.meal.planner.model.water.QuickWater
import com.meal.planner.model.water.QuickWaterList
import com.meal.planner.model.water.ReadableFrequentDrink
import com.meal.planner.utils.PreferenceProvider
import com.meal.planner.utils.water.WaterCounter
import com.meal.planner.utils.water.WaterRateProvider
import java.util.*
import kotlin.collections.ArrayList

class WaterVM(application: Application) : AndroidViewModel(application) {

    private var quickWaterData: MutableLiveData<QuickWaterList>? = null
    private var dailyRate: MutableLiveData<Int>? = null  // current daily rate
    private var currentCapacity: MutableLiveData<Int>? = null  // current capacity
    private var globalWaterCapacity: MutableLiveData<Int>? = null
    private var frequentDrink: MutableLiveData<ReadableFrequentDrink>? = null
    private var marathonDays: MutableLiveData<Int>? = null

    private val TYPE_WATER = 0
    private val TYPE_COFFEE = 2
    private val TYPE_SODA = 4
    private val TYPE_BLACK_TEA = 3

    private val CAPACITY_200_ML = 3
    private val CAPACITY_250_ML = 4
    private val CAPACITY_300_ML = 5


    private fun reloadQuickLD() {
        if (PreferenceProvider.getQuickData(0) == -1) {
            PreferenceProvider.setQuickData(TYPE_WATER, 0)
            PreferenceProvider.setCapacityIndex(CAPACITY_200_ML, 0)

            PreferenceProvider.setQuickData(TYPE_COFFEE, 1)
            PreferenceProvider.setCapacityIndex(CAPACITY_250_ML, 1)

            PreferenceProvider.setQuickData(TYPE_SODA, 2)
            PreferenceProvider.setCapacityIndex(CAPACITY_200_ML, 2)

            PreferenceProvider.setQuickData(TYPE_BLACK_TEA, 3)
            PreferenceProvider.setCapacityIndex(CAPACITY_300_ML, 3)
        }
        fillQuickWaterData()
    }

    private fun fillQuickWaterData() {
        var waterList = QuickWaterList(arrayListOf())
        for (i in 0..3) {
            var index = PreferenceProvider.getQuickData(i)!!
            waterList.list.add(QuickWater(getQuickName(index), getImgId(index), getDrinkFactor(index), getCapacity(i), index))
        }
        quickWaterData!!.value = waterList
    }

    private fun getDrinkFactor(index: Int): Float {
        var percent = getApplication<App>()
                .resources
                .getIntArray(R.array.water_drinks_factor)[index]
        return percent / 100F
    }

    private fun getCapacity(i: Int): Int {
        return getApplication<App>()
                .resources
                .getIntArray(R.array.drink_capacity_quantity)[PreferenceProvider.getCapacityIndex(i)!!]
    }

    private fun getImgId(index: Int): Int {
        return getApplication<App>()
                .resources
                .obtainTypedArray(R.array.water_drinks_imgs_color)
                .getResourceId(index, -1)
    }

    private fun getQuickName(index: Int): String {
        return getApplication<App>()
                .resources
                .getStringArray(R.array.water_drinks_names)[index]
    }

    fun getMarathonDaysLD(): MutableLiveData<Int> {
        if (marathonDays == null) {
            marathonDays = MutableLiveData()
            fillMarathonDays()
        }
        return marathonDays!!
    }

    fun reCalculateMarathonDays() {
        if (marathonDays == null) {
            marathonDays = MutableLiveData()
        }
        fillMarathonDays()
    }

    private fun fillMarathonDays() {
        var intakes = App.getInstance().db.dietDAO().getAllWaterIntakes()
        var rates = App.getInstance().db.dietDAO().getAllWaterRates()

        var days = WaterCounter.getMarathonDays(ArrayList(intakes), ArrayList(rates))
        marathonDays!!.value = days

    }

    fun getQuickLD(): MutableLiveData<QuickWaterList> {
        if (quickWaterData == null) {
            quickWaterData = MutableLiveData()
            reloadQuickLD()
        }
        return quickWaterData!!
    }

    fun getDailyRate(): MutableLiveData<Int> {
        if (dailyRate == null) {
            dailyRate = MutableLiveData()
        }
        countWaterRateDefault()
        return dailyRate!!
    }

    fun getCurrentCapacity(): MutableLiveData<Int> {
        if (currentCapacity == null) {
            currentCapacity = MutableLiveData()
            calculateCurrentCapacity()
        }
        return currentCapacity!!
    }

    fun getGlobalWaterCapacity(): MutableLiveData<Int> {
        if (globalWaterCapacity == null) {
            globalWaterCapacity = MutableLiveData()
            calculateGlobalCapacity()
        }
        return globalWaterCapacity!!
    }

    fun getFrequentDrink(): MutableLiveData<ReadableFrequentDrink> {
        if (frequentDrink == null) {
            frequentDrink = MutableLiveData()
            identifyFrequentDrink()
        }
        return frequentDrink!!
    }


    private fun calculateCurrentCapacity() {
        var cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        var min = cal.timeInMillis

        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 59)

        var max = cal.timeInMillis

        var allIntakes = App.getInstance().db.dietDAO().getCurrentWaterIntakes(min, max)
        if (allIntakes != null) {
            currentCapacity!!.value = calulateCapacity(allIntakes)
        } else {
            currentCapacity!!.value = 0
        }
    }

    private fun calulateCapacity(allIntakes: List<WaterIntake>): Int {
        var sum = 0
        for (i in allIntakes.indices) {
            sum += allIntakes[i].clearCapacity
        }
        return sum
    }


    fun saveNewQuickItem(selectedCapacity: Int, selectedDrinkType: Int, numberQuickItem: Int) {
        PreferenceProvider.setQuickData(selectedDrinkType, numberQuickItem)
        PreferenceProvider.setCapacityIndex(selectedCapacity, numberQuickItem)
        reloadQuickLD()
    }

    fun changeHotFactor(isHotOn: Boolean) {
        PreferenceProvider.setHotFactor(isHotOn)
        WaterRateProvider.addNewRate(countWaterRateDefault())
    }

    private fun countWaterRateDefault(): Int {
        var value = WaterCounter.getWaterDailyRate(PreferenceProvider.getSex()!!, PreferenceProvider.getTrainingFactor()!!,
                PreferenceProvider.getHotFactor()!!, PreferenceProvider.getWeight()!!, true)

        dailyRate!!.value = value
        return value
    }

    fun changeTrainingFactor(isTrainingOn: Boolean) {
        PreferenceProvider.setTrainingFactor(isTrainingOn)
        WaterRateProvider.addNewRate(countWaterRateDefault())
    }

    fun firstCalculateWaterRate() {
        WaterRateProvider.addNewRate(countWaterRateDefault())
    }


    fun addWater(position: Int) {
        var quickDrink = getQuickLD().value!!.list[position]
        var clearWater = (quickDrink.capacity * quickDrink.drinkFactor).toInt()
        currentCapacity!!.value = currentCapacity!!.value!! + clearWater

        App
                .getInstance()
                .db
                .dietDAO()
                .addWater(WaterIntake(Calendar.getInstance().timeInMillis, quickDrink.typeId, quickDrink.capacity, clearWater))

        increaseGlobalWater(clearWater) /// increase current day capacity of clear water

        var drinksCapacities = App.getInstance().db.dietDAO().getChoiceDrink(quickDrink.typeId)
        var capacity = 0L
        if (drinksCapacities?.size > 0) {
            for (i in drinksCapacities.indices) {
                capacity += drinksCapacities[i].dirtyCapacity
            }
        }
        capacity += quickDrink.capacity
        App.getInstance().db.dietDAO().insertTypeDrink(DrinksCapacities(quickDrink.typeId, capacity))
        identifyFrequentDrink() // increase capacity of certain drink (global, for all time)

        PreferenceProvider.lastTimeWaterIntake = Calendar.getInstance().timeInMillis
    }

    private fun identifyFrequentDrink() {
        var drinkCapacity = App.getInstance().db.dietDAO().getBiggestDrink()
        if (drinkCapacity?.size > 0) {
            var name = getApplication<App>().applicationContext.resources.getStringArray(R.array.water_drinks_names)[drinkCapacity[0].typeDrink]
            var capacity = "${(drinkCapacity[0].dirtyCapacity.toFloat() / 1000)} л"
            frequentDrink?.value = ReadableFrequentDrink(name, capacity)
        }
    }

    private fun increaseGlobalWater(clearWater: Int) {
        var capacity = PreferenceProvider.getGlobalWaterCount()!!
        capacity += clearWater
        PreferenceProvider.setGlobalWaterCount(capacity)

        globalWaterCapacity!!.value = capacity / 1000
    }

    private fun calculateGlobalCapacity() {
        var capacity = PreferenceProvider.getGlobalWaterCount()!!
        globalWaterCapacity!!.value = capacity / 1000
    }

}