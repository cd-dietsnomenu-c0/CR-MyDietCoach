package com.wsoteam.mydietcoach.diets.controllers

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.diets.ItemClick
import java.util.ArrayList

class SectionAdapter(var sectionList: ArrayList<Section>, var leftDrawables: TypedArray, var itemClick: ItemClick) : Adapter<SectionVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionVH {
        val inflater = LayoutInflater.from(parent.context)
        return SectionVH(inflater, parent, itemClick)
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: SectionVH, position: Int) {
        holder.bind(sectionList[position], leftDrawables.getResourceId(sectionList[position].urlOfImage.toInt(), -1))
    }
}