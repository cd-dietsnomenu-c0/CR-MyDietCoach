package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback

class AnimBacksAdapter(val listBacks: Array<String>, val param: ClickBackCallback) : RecyclerView.Adapter<AnimBackVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimBackVH {
        val li = LayoutInflater.from(parent.context)
        return AnimBackVH(li, parent, param)
    }

    override fun onBindViewHolder(holder: AnimBackVH, position: Int) {
        holder.bind(listBacks[position])
    }

    override fun getItemCount(): Int {
        return listBacks.size
    }
}