package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diets.weightloss.App
import com.diets.weightloss.model.water.WaterMarathon
import com.diets.weightloss.utils.water.WaterCounter

class MarathonVM(application: Application) : AndroidViewModel(application) {

    private var marathons: MutableLiveData<List<WaterMarathon>>? = null

    fun getMarathons(): MutableLiveData<List<WaterMarathon>> {
        if (marathons == null) {
            marathons = MutableLiveData()
            fillMarathons()
        }
        return marathons!!
    }

    private fun fillMarathons() {
        var list = WaterCounter.getSortedMarathons(ArrayList(App.getInstance().db.dietDAO().getAllWaterIntakes()), ArrayList(App.getInstance().db.dietDAO().getAllWaterRates()))
        marathons!!.value = list
    }

    /*private fun fillReadableCapacities(list: List<WaterMarathon>): List<WaterMarathon> {

    }*/
}