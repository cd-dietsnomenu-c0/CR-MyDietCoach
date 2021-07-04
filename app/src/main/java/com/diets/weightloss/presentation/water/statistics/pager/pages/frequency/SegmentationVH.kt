package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diets.weightloss.App
import com.diets.weightloss.R
import com.diets.weightloss.common.db.entities.water.DrinksCapacities
import java.text.DecimalFormat

class SegmentationVH(application: Application) : AndroidViewModel(application) {

    private var capacities: MutableLiveData<List<DrinksCapacities>>? = null

    fun getCapacities(): MutableLiveData<List<DrinksCapacities>> {
        if (capacities == null) {
            capacities = MutableLiveData()
            fillVM()
        }
        return capacities!!
    }

    private fun fillVM() {
        var list = App.getInstance().db.dietDAO().getAllCapacities()
        list = fillImages(list)
        list = fillNames(list)
        list = fillCapacities(list)
        list = fillGlobalParts(list)
        capacities!!.value = list
    }

    private fun fillGlobalParts(list: List<DrinksCapacities>): List<DrinksCapacities> {
        var globalCapacity = 0L
        var formatter = DecimalFormat("#0.0")
        for (drink in list) {
            globalCapacity += drink.dirtyCapacity
        }

        for (i in list.indices) {
            var value = (globalCapacity / list[i].dirtyCapacity).toFloat()
            list[i].globalPart = getApplication<App>().applicationContext.resources.getString(R.string.global_part, "${formatter.format(value)}")
        }
        return list
    }

    private fun fillCapacities(list: List<DrinksCapacities>): List<DrinksCapacities> {
        var formatter = DecimalFormat("#0.0")
        for (i in list.indices) {
            list[i].readableCapacity =
                    getApplication<App>()
                            .applicationContext
                            .getString(R.string.capacity_unit, "${formatter.format(list[i].dirtyCapacity / 1000f)}")
        }
        return list
    }

    private fun fillNames(list: List<DrinksCapacities>): List<DrinksCapacities> {
        for (i in list.indices) {
            list[i].readableName =
                    getApplication<App>()
                            .applicationContext.resources
                            .getStringArray(R.array.water_drinks_names)[list[i].typeDrink]
        }
        return list
    }

    private fun fillImages(list: List<DrinksCapacities>): List<DrinksCapacities> {
        for (i in list.indices) {
            list[i].imgIndex =
                    getApplication<App>()
                            .applicationContext.resources
                            .obtainTypedArray(R.array.water_drinks_imgs_color).getResourceId(list[i].typeDrink, -1)
        }
        return list
    }
}