package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.Eat

class EatAdapter(var listEat : List<Eat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EatVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listEat.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EatVH).bind(listEat[position])
    }
}