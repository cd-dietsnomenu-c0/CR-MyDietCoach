package com.diets.weightloss.presentation.history.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.common.db.entities.DietHistory

class HistoryDietAdapter(val dietList : List<DietHistory>) : RecyclerView.Adapter<HistoryDietVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDietVH {
        var inflater = LayoutInflater.from(parent.context)
        return HistoryDietVH(inflater, parent)
    }

    override fun onBindViewHolder(holder: HistoryDietVH, position: Int) {
        holder.bind(dietList[position])
    }

    override fun getItemCount(): Int {
        return dietList.size
    }
}