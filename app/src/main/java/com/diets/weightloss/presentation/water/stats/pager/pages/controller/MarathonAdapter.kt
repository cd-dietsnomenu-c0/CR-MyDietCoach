package com.diets.weightloss.presentation.water.stats.pager.pages.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MarathonAdapter() : RecyclerView.Adapter<MarathonHeadVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarathonHeadVH {
        val inflater = LayoutInflater.from(parent.context)
        return MarathonHeadVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: MarathonHeadVH, position: Int) {
    }
}