package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AnimBacksAdapter(val listBacks : Array<String>) : RecyclerView.Adapter<AnimBackVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimBackVH {
        val li = LayoutInflater.from(parent.context)
        return AnimBackVH(li, parent)
    }

    override fun onBindViewHolder(holder: AnimBackVH, position: Int) {
        holder.bind(listBacks[position])
    }

    override fun getItemCount(): Int {
        return listBacks.size
    }
}