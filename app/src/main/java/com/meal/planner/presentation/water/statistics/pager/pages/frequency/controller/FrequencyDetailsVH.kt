package com.meal.planner.presentation.water.statistics.pager.pages.frequency.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meal.planner.R
import com.meal.planner.common.db.entities.water.DrinksCapacities
import kotlinx.android.synthetic.main.frequency_details_vh.view.*
import java.text.DecimalFormat

class FrequencyDetailsVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.frequency_details_vh, viewGroup, false)) {
    fun onBind(drinksCapacity: DrinksCapacities) {
        var formatter = DecimalFormat("#0.00")

        itemView.tvName.text = drinksCapacity.readableName
        itemView.tvValue.text = drinksCapacity.readableCapacity
        itemView.tvFrequency.text = itemView.resources.getString(R.string.global_part, "${formatter.format(drinksCapacity.globalPart)}")
        itemView.ivDrink.setImageResource(drinksCapacity.imgIndex)

    }
}