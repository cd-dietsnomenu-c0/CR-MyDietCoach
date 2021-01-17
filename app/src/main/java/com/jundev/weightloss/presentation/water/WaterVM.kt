package com.jundev.weightloss.presentation.water

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jundev.weightloss.App
import com.jundev.weightloss.R
import com.jundev.weightloss.model.water.QuickWater
import com.jundev.weightloss.model.water.QuickWaterList
import com.jundev.weightloss.utils.PreferenceProvider

class WaterVM(application: Application) : AndroidViewModel(application) {

    private var quickWaterData: MutableLiveData<QuickWaterList>? = null

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
            waterList.list.add(QuickWater(getQuickName(index), getImgId(index), getDrinkFactor(index), getCapacity(i)))
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


    fun getLD(): MutableLiveData<QuickWaterList> {
        if (quickWaterData == null) {
            quickWaterData = MutableLiveData()
            reloadQuickLD()
        }
        return quickWaterData!!
    }

    fun saveNewQuickItem(selectedCapacity : Int, selectedDrinkType : Int, numberQuickItem : Int){
        PreferenceProvider.setQuickData(selectedDrinkType, numberQuickItem)
        PreferenceProvider.setCapacityIndex(selectedCapacity, numberQuickItem)
        reloadQuickLD()
    }
}