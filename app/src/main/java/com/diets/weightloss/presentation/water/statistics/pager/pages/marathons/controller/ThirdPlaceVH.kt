package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.R
import com.diets.weightloss.model.water.WaterMarathon
import kotlinx.android.synthetic.main.item_marathon_third.view.*


class ThirdPlaceVH (layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_marathon_third, viewGroup, false)) {
    fun bind(waterMarathon: WaterMarathon) {
        itemView.tvInterval.text = "${waterMarathon.readableStart} - ${waterMarathon.readableEnd}"
        itemView.tvCapacity.text = itemView.resources.getQuantityString(R.plurals.days_plur, waterMarathon.duration, waterMarathon.duration)
        itemView.tvDuration.text = itemView.resources.getString(R.string.capacity_unit, waterMarathon.readableCapacity)
    }

}