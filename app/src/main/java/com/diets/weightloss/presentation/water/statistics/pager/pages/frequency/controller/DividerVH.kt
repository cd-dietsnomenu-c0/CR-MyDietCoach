package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.divider_vh.view.*

class DividerVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.divider_vh, viewGroup, false)) {
    fun onBind(text: String) {
        itemView.tvDivider.text = text
    }
}