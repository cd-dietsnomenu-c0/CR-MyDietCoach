package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(var count : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var residue = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DayVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        residue = count % 5
        var count = count / 5
        if (residue > 0){
            count += 1
        }
        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayVH).bind(getSpanCount(position))
    }

    private fun getSpanCount(position: Int): Int {
        if (position < itemCount - 1){
            return 5
        }else if (residue > 0){
            return residue
        }else {
            return 0
        }
    }
}