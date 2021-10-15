package com.diets.weightloss.presentation.history.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.common.db.entities.HistoryDiet

class HistoryDietAdapter(val listDiet: List<HistoryDiet>, val clickListener: HistoryClickListener) : RecyclerView.Adapter<HistoryDietVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDietVH {
        var inflater = LayoutInflater.from(parent.context)
        return HistoryDietVH(inflater, parent, clickListener)
    }

    override fun onBindViewHolder(holder: HistoryDietVH, position: Int) {
        holder.bind(listDiet[position])
    }

    override fun getItemCount(): Int {
        return listDiet.size
    }
}