package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_tracker_day.view.*

class DaysVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_tracker_day, viewGroup, false)) {

    fun bind(lastPosition: Boolean) {
        if (lastPosition){
            val params : FrameLayout.LayoutParams = itemView.tvNumberDay.layoutParams as FrameLayout.LayoutParams
            params.setMargins(params.leftMargin, params.topMargin, 26, params.bottomMargin)
            itemView.flParent.layoutParams = params
        }
    }
}