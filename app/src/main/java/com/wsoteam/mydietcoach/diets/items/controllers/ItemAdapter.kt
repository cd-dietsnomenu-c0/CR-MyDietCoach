package com.wsoteam.mydietcoach.diets.items.controllers

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.diets.ItemClick

class ItemAdapter(val list: ArrayList<Subsection>,  var drawables: TypedArray, var itemClick: ItemClick) : RecyclerView.Adapter<ItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        var inflater = LayoutInflater.from(parent.context)
        return ItemVH(inflater, parent, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.bind(list[position], drawables.getResourceId(list[position].urlOfImage.toInt(), -1))
    }
}