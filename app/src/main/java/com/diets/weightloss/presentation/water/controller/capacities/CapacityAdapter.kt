package com.diets.weightloss.presentation.water.controller.capacities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CapacityAdapter(val capacitiesValues: Array<String>, val imgsIds: ArrayList<Int>, val iCapacityAdapter: ICapacityAdapter, selectedItem : Int) : RecyclerView.Adapter<CapacityVH>() {

    private var lastSelect = 0

    init {
        lastSelect = selectedItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapacityVH {
        var infalter = LayoutInflater.from(parent.context)
        return CapacityVH(infalter, parent, object : ICapacityAdapter{
            override fun select(newSelect: Int, oldSelect: Int) {
                iCapacityAdapter.select(newSelect, lastSelect)
                lastSelect = newSelect
            }
        })
    }

    override fun getItemCount(): Int {
        return capacitiesValues.size
    }

    override fun onBindViewHolder(holder: CapacityVH, position: Int) {
        holder.bind(capacitiesValues[position], imgsIds[position], position == lastSelect)
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