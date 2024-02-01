package com.calorie.dieta.presentation.water.statistics.pager.pages.frequency.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calorie.dieta.R
import kotlinx.android.synthetic.main.divider_vh.view.*

class DividerVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.divider_vh, viewGroup, false)) {
    fun onBind() {
        itemView.tvDivider.text = itemView.resources.getString(R.string.details)
    }
}