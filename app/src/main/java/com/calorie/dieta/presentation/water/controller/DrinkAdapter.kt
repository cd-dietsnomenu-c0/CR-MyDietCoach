package com.calorie.dieta.presentation.water.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DrinkAdapter(val names: Array<String>, var selectedItem: Int, val iDrinkAdapter: IDrinkAdapter) : RecyclerView.Adapter<DrinkVH>() {

    private var lastSelect = 0

    init {
        lastSelect = selectedItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkVH {
        val inflater = LayoutInflater.from(parent.context)
        return DrinkVH(inflater, parent, object : IDrinkAdapter{
            override fun select(newSelect: Int, oldSelect: Int) {
                iDrinkAdapter.select(newSelect, lastSelect)
                lastSelect = newSelect
            }
        })
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: DrinkVH, position: Int) {
        holder.bind(position, names[position], position == lastSelect)
    }

    fun unSelect(oldSelect: Int) {
        notifyItemChanged(oldSelect)
    }

    fun selectNew(position: Int){
        lastSelect = position
        notifyDataSetChanged()
    }

    fun getSelectedNumber() : Int{
        return lastSelect
    }
}