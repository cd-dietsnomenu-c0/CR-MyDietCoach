package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback

class BacksAdapter(val urls : Array<String>, val iBacks: ClickBackCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BacksVH(LayoutInflater.from(parent.context), parent, iBacks)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BacksVH).bind(urls[position])
    }
}