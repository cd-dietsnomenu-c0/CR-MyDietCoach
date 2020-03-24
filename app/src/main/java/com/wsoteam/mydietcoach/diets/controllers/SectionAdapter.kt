package com.wsoteam.mydietcoach.diets.controllers

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.diets.ItemClick
import java.util.ArrayList

class SectionAdapter(var sectionList: ArrayList<Section>, var leftDrawables: Array<String>, var itemClick: ItemClick) : Adapter<RecyclerView.ViewHolder>() {
    val HEAD_TYPE = 0
    val BODY_TYPE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            HEAD_TYPE -> HeadVH(inflater, parent)
            else -> SectionVH(inflater, parent, itemClick)
        }
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeadVH -> holder.bind()
            is SectionVH -> holder.bind(sectionList[position], leftDrawables[sectionList[position].urlOfImage.toInt()])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}