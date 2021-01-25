package com.jundev.weightloss.presentation.water

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jundev.weightloss.App
import com.jundev.weightloss.R
import com.jundev.weightloss.common.db.entities.WaterIntake
import com.jundev.weightloss.model.water.QuickWater
import com.jundev.weightloss.model.water.QuickWaterList
import com.jundev.weightloss.utils.PreferenceProvider
import java.util.*

class WaterVM(application: Application) : AndroidViewModel(application) {

    private var quickWaterData: MutableLiveData<QuickWaterList>? = null
    private var dailyRate: MutableLiveData<Int>? = null  // current daily rate
    private var currentCapacity: MutableLiveData<Int>? = null  // current capacity

    private val FEMALE_FACTOR = 31
    private val MALE_FACTOR = 35

    private val TRAINING_FACTOR = 0.1f
    private val HOT_FACTOR = 0.15f

    private fun reloadQuickLD() {
        if (PreferenceProvider.getQuickData(0) == -1) {
            PreferenceProvider.setQuickData(0, 0)
            PreferenceProvider.setCapacityIndex(1, 0)

            PreferenceProvider.setQuickData(3, 1)
            PreferenceProvider.setCapacityIndex(6, 1)

            PreferenceProvider.setQuickData(4, 2)
            PreferenceProvider.setCapacityIndex(2, 2)

            PreferenceProvider.setQuickData(7, 3)
            PreferenceProvider.setCapacityIndex(3, 3)
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
        Log.e("LOL", "get list")

        showAllWaters()
    }

    private fun showAllWaters() {
        var list = App.getInstance().db.dietDAO().getAllWaterIntakes()
        for (i in list.indices){
            Log.e("LOL", list[i].toString())
        }
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


    fun getQuickLD(): MutableLiveData<QuickWaterList> {
        if (quickWaterData == null) {
            quickWaterData = MutableLiveData()
            reloadQuickLD()
        }
        return quickWaterData!!
    }

    fun getDailyRate() : MutableLiveData<Int>{
        if (dailyRate == null) {
            dailyRate = MutableLiveData()
            countWaterRateDefault()
        }
        return dailyRate!!
    }

    fun getCurrentCapacity() : MutableLiveData<Int>{
        if (currentCapacity == null) {
            currentCapacity = MutableLiveData()
            currentCapacity!!.value = 0
        }
        return currentCapacity!!
    }


    fun saveNewQuickItem(selectedCapacity: Int, selectedDrinkType: Int, numberQuickItem: Int) {
        PreferenceProvider.setQuickData(selectedDrinkType, numberQuickItem)
        PreferenceProvider.setCapacityIndex(selectedCapacity, numberQuickItem)
        reloadQuickLD()
    }

    fun changeHotFactor(isHotOn: Boolean){
        PreferenceProvider.setHotFactor(isHotOn)
        countWaterRateDefault()
    }

    private fun countWaterRateDefault() {
        countWaterRate(PreferenceProvider.getSex()!!, PreferenceProvider.getTrainingFactor()!!,
                PreferenceProvider.getHotFactor()!!, PreferenceProvider.getWeight()!!)
    }

    fun changeTrainingFactor(isTrainingOn: Boolean){
        PreferenceProvider.setTrainingFactor(isTrainingOn)
        countWaterRateDefault()
    }

    private fun countWaterRate(gender: Int, isTrainingOn : Boolean, isHotOn : Boolean, weight : Int){
        var factor = if (gender == PreferenceProvider.SEX_TYPE_FEMALE){
            FEMALE_FACTOR
        }else{
            MALE_FACTOR
        }

        var waterRate = weight * factor
        var trainingDiff = 0
        var hotDiff = 0

        if (isHotOn){
            hotDiff = (waterRate * HOT_FACTOR).toInt()
        }

        if (isTrainingOn){
            trainingDiff = (waterRate * TRAINING_FACTOR).toInt()
        }

        waterRate += trainingDiff + hotDiff
        dailyRate!!.value = waterRate
    }

    fun addWater(position: Int) {
        var quickDrink = getQuickLD().value!!.list[position]
        var clearWater = (quickDrink.capacity * quickDrink.drinkFactor).toInt()
        currentCapacity!!.value = currentCapacity!!.value!! + clearWater

        App.getInstance().db.dietDAO().addWater(WaterIntake(Calendar.getInstance().timeInMillis.toInt(), quickDrink.typeId, quickDrink.capacity, clearWater))
    }
}