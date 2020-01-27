package com.wsoteam.mydietcoach.diets.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_of_sections.view.*

class SectionVH(inflater: LayoutInflater, viewGroup: ViewGroup) :
        ViewHolder(inflater.inflate(R.layout.item_of_sections, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(section: Section, id: Int) {
        itemView.tv_item.text = section.description
        Glide.with(itemView.context).load(id).into(itemView.iv_item)
    }

    override fun onClick(p0: View?) {

    }
}