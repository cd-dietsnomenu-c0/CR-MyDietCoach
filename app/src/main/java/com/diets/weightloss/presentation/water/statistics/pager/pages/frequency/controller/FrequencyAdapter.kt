package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency.controller

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.common.db.entities.water.DrinksCapacities
import com.diets.weightloss.presentation.diets.controller.ADVH
import com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.controller.NativeWaterVH
import com.yandex.mobile.ads.nativeads.NativeAd
import java.util.*
import kotlin.math.PI

class FrequencyAdapter(val drinks: List<DrinksCapacities>, val centerPieText: SpannableString, val showedDrinks: List<DrinksCapacities>, val otherValue: Float, var nativeList: ArrayList<NativeAd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var counter = 0

    companion object {
        private const val PIE_TYPE = 0
        private const val DIVIDER_TYPE = 1
        private const val FREQUNECY_DETAIL_TYPE = 2
        private const val AD_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PIE_TYPE -> PieChartVH(inflater, parent)
            DIVIDER_TYPE -> DividerVH(inflater, parent)
            FREQUNECY_DETAIL_TYPE -> FrequencyDetailsVH(inflater, parent)
            AD_TYPE -> ADVH(inflater, parent)
            else -> PieChartVH(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PIE_TYPE -> (holder as PieChartVH).onBind(showedDrinks, centerPieText, otherValue)
            DIVIDER_TYPE -> (holder as DividerVH).onBind()
            FREQUNECY_DETAIL_TYPE -> (holder as FrequencyDetailsVH).onBind(drinks[position - 2])
            AD_TYPE -> (holder as ADVH).bind(nativeList[getAdPosition()])
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }


    private fun getAdPosition(): Int {
        var position = 0
        if (counter > nativeList.size - 1) {
            position = 0
            counter = 1
        } else {
            position = counter
            counter++
        }
        return position
    }

    override fun getItemCount(): Int {
        return if (nativeList.isEmpty()) {
            drinks.size + 2
        } else {
            drinks.size + 3
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> PIE_TYPE
            1 -> DIVIDER_TYPE
            drinks.size + 2 -> AD_TYPE
            else -> FREQUNECY_DETAIL_TYPE
        }
    }
}