package com.wsoteam.mydietcoach.calculators.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_of_list_calculating.view.*
import java.io.FileDescriptor

class CalculatingVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, var clicker : ClickItem) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_of_list_calculating, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clicker.click(adapterPosition)
    }

    fun bind(title: String, description : String, gradient : Int){
        itemView.tvTitleOfItemListCalculating.text = title
        itemView.tvDescriptionOfItemListCalculating.text = description
        itemView.llParent.setBackgroundResource(gradient)
    }
}