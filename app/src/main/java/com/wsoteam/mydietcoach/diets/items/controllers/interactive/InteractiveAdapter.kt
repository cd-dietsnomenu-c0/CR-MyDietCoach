package com.wsoteam.mydietcoach.diets.items.controllers.interactive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.AllDiets
import com.wsoteam.mydietcoach.diets.ItemClick

class InteractiveAdapter(val allDiets: AllDiets, var itemClick: ItemClick) : RecyclerView.Adapter<InteractiveVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteractiveVH {
        val inflater = LayoutInflater.from(parent.context)
        return InteractiveVH(inflater, parent, itemClick)
    }

    override fun getItemCount(): Int {
        return allDiets.dietList.size
    }

    override fun onBindViewHolder(holder: InteractiveVH, position: Int) {
        holder.bind(allDiets.dietList[position].title, allDiets.dietList[position].mainImage)
    }
}