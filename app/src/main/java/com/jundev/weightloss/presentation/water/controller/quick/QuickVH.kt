package com.jundev.weightloss.presentation.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.item_quick.view.*

class QuickVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, iQuick: IQuick)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_quick, viewGroup, false)) {

    init {
        itemView.ivSettings.setOnClickListener {
            iQuick.onSettings(adapterPosition)
        }

        itemView.btnAdd.setOnClickListener {
            iQuick.onAdd(adapterPosition)
        }
    }

    fun bind(imgIndex : Int, title : String, capacity : Int){
        itemView.tvTitle.text = title
        itemView.tvCapacity.text = "$capacity мл"
        itemView.ivHead.setImageResource(imgIndex)
    }

}