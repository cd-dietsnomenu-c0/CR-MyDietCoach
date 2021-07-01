package com.diets.weightloss.presentation.water.stats.pager.pages.marathons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diets.weightloss.model.water.WaterMarathon

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

    }
}