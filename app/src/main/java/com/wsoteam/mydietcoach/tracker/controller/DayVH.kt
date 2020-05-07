package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_tracker_day.view.*

class DayVH(var layoutInflater: LayoutInflater, var viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_tracker_day, viewGroup, false)) {

    fun bind(count: Int) {
        if (count < 5) itemView.cvFifth.visibility = View.GONE
        if (count < 4) itemView.cvFourth.visibility = View.GONE
        if (count < 3) itemView.cvThird.visibility = View.GONE
        if (count < 2) itemView.cvSecond.visibility = View.GONE
    }

}