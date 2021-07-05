package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency.controller

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.common.db.entities.water.DrinksCapacities

class FrequencyAdapter(val drinks: List<DrinksCapacities>, val centerPieText: SpannableString, val showedDrinks : List<DrinksCapacities>, val otherValue : Float) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val PIE_TYPE = 0
        private const val DIVIDER_TYPE = 1
        private const val FREQUNECY_DETAIL_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PIE_TYPE -> PieChartVH(inflater, parent)
            DIVIDER_TYPE -> DividerVH(inflater, parent)
            FREQUNECY_DETAIL_TYPE -> FrequencyDetailsVH(inflater, parent)
            else -> PieChartVH(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PIE_TYPE -> (holder as PieChartVH).onBind(showedDrinks, centerPieText, otherValue)
            DIVIDER_TYPE -> (holder as DividerVH).onBind()
            FREQUNECY_DETAIL_TYPE -> (holder as FrequencyDetailsVH).onBind(drinks[position - 2])
        }
    }

    override fun getItemCount(): Int {
        return drinks.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) {
            DIVIDER_TYPE
        } else if (position == 0) {
            PIE_TYPE
        } else {
            FREQUNECY_DETAIL_TYPE
        }
    }
}