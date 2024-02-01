package com.calorie.dieta.presentation.diets.list.old.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calorie.dieta.R
import com.calorie.dieta.presentation.diets.list.ItemClick
import kotlinx.android.synthetic.main.head_vh.view.*

class HeadVH(inflater: LayoutInflater, viewGroup: ViewGroup, var itemClick: ItemClick) : RecyclerView.ViewHolder(inflater.inflate(R.layout.head_vh, viewGroup, false )) {
    fun bind() {
        itemView.button.setOnClickListener {
            itemClick.newDietsClick()
        }
    }
}