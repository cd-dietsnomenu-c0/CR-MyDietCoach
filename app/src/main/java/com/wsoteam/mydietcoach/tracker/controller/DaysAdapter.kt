package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DaysAdapter() : RecyclerView.Adapter<DaysVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysVH {
        val inflater = LayoutInflater.from(parent.context)
        return DaysVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: DaysVH, position: Int) {
        holder.bind(isLastPosition(position))
    }

    private fun isLastPosition(position: Int): Boolean {
        return position == itemCount - 1
    }
}