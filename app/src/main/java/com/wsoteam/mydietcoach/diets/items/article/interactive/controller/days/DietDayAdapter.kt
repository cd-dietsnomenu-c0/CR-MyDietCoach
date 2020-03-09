package com.wsoteam.mydietcoach.diets.items.article.interactive.controller.days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay

class DietDayAdapter(var days: List<DietDay>) : RecyclerView.Adapter<DietDayVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietDayVH {
        val inflater = LayoutInflater.from(parent.context)
        return DietDayVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: DietDayVH, position: Int) {
        holder.bind(days[position])
    }
}