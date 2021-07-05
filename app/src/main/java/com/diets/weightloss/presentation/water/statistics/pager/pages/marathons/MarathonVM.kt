package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diets.weightloss.App
import com.diets.weightloss.model.water.WaterMarathon
import com.diets.weightloss.utils.water.WaterCounter
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

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
        list = fillReadableCapacities(list)
        list = fillReadableTimes(list)
        marathons!!.value = list.sortedByDescending { it.duration }
    }

    private fun fillReadableTimes(list: List<WaterMarathon>): List<WaterMarathon> {
        for (i in list.indices) {
            list[i].readableStart = getReadableDate(list[i].start)
            list[i].readableEnd = getReadableDate(list[i].end)
        }
        return list
    }

    private fun getReadableDate(timeInMillis: Long): String {
        var cal = Calendar.getInstance()
        cal.timeInMillis = timeInMillis
        return "${String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))}.${String.format("%02d", cal.get(Calendar.MONTH))}.${cal.get(Calendar.YEAR)}"
    }

    private fun fillReadableCapacities(list: List<WaterMarathon>): List<WaterMarathon> {
        var formater = DecimalFormat("#0.0")
        for (i in list.indices) {
            Log.e("LOL", list[i].capacity.toString())
            list[i].readableCapacity = formater.format(list[i].capacity / 1000f)
        }
        return list
    }
}