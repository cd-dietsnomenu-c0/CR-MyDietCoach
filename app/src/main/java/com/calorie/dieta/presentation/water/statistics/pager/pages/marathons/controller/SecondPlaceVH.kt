package com.calorie.dieta.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calorie.dieta.R
import com.calorie.dieta.model.water.WaterMarathon
import kotlinx.android.synthetic.main.item_marathon.view.*

class SecondPlaceVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_marathon, viewGroup, false)) {
    fun bind(waterMarathon: WaterMarathon) {
        itemView.tvInterval.text = "${waterMarathon.readableStart} - ${waterMarathon.readableEnd}"
        itemView.tvDuration.text = itemView.resources.getQuantityString(R.plurals.days_plur, waterMarathon.duration, waterMarathon.duration)
        itemView.tvCapacity.text = itemView.resources.getString(R.string.capacity_unit, waterMarathon.readableCapacity)
    }

}