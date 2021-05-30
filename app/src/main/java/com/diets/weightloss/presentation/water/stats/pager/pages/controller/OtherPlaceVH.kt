package com.diets.weightloss.presentation.water.stats.pager.pages.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.item_marathon_other.view.*

class OtherPlaceVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_marathon_other, viewGroup, false)) {
    fun bind(position: Int) {
        itemView.tvPlace.text = "${adapterPosition + 1}"
    }
}