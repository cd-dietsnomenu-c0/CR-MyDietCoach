package com.meal.planner.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meal.planner.R
import com.meal.planner.model.water.WaterMarathon
import kotlinx.android.synthetic.main.marathon_head_vh.view.*

class FirstPlaceVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.marathon_head_vh, viewGroup, false)) {

    fun bind(waterMarathon: WaterMarathon) {
        itemView.tvDays.text = waterMarathon.duration.toString()
        itemView.tvDays.text = itemView.resources.getQuantityString(R.plurals.days_plur, waterMarathon.duration, waterMarathon.duration)
        itemView.tvCapacity.text = itemView.resources.getString(R.string.capacity_unit, waterMarathon.readableCapacity)
        itemView.tvStart.text = waterMarathon.readableStart
        itemView.tvEnd.text = waterMarathon.readableEnd
    }
}