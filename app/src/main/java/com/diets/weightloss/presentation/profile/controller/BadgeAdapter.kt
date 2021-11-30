package com.diets.weightloss.presentation.profile.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BadgeAdapter() : RecyclerView.Adapter<BadgeVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeVH {
        var li = LayoutInflater.from(parent.context)
        return BadgeVH(li, parent)
    }

    override fun onBindViewHolder(holder: BadgeVH, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }
}