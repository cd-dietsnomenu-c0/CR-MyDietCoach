package com.wsoteam.mydietcoach.diets.list.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.list.ItemClick
import kotlinx.android.synthetic.main.item_of_sections.view.*

class SectionVH(inflater: LayoutInflater, viewGroup: ViewGroup, var itemClick: ItemClick) :
        ViewHolder(inflater.inflate(R.layout.item_of_sections, viewGroup, false)), View.OnClickListener {


    init {
        itemView.setOnClickListener(this)
    }

    fun bind(section: Section, id: String) {
        itemView.tv_item.text = section.description
        Glide.with(itemView.context).load(id).into(itemView.iv_item)
    }

    override fun onClick(p0: View?) {
        itemClick.click(adapterPosition)
    }
}