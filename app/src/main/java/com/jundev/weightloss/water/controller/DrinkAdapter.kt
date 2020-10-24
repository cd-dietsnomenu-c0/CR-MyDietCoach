package com.jundev.weightloss.water.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DrinkAdapter(val names: Array<String>, val hydroFactors: IntArray) : RecyclerView.Adapter<DrinkVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkVH {
        val inflater = LayoutInflater.from(parent.context)
        return DrinkVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: DrinkVH, position: Int) {
        holder.bind(position, names[position], position, hydroFactors[position])
    }
}