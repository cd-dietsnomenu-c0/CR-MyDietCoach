package com.wsoteam.mydietcoach.diets.list.modern.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.list.ItemClick
import kotlinx.android.synthetic.main.interactive_vh.view.*

class InteractiveVH(inflater: LayoutInflater, viewGroup: ViewGroup, val itemClick: ItemClick) : RecyclerView.ViewHolder(inflater.inflate(R.layout.interactive_vh, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(title: String, mainImage: String, isNew: Boolean) {
        Glide.with(itemView.context).load(mainImage).into(itemView.ivBackground)
        itemView.tvTitle.text = title
        if (isNew){
            itemView.ivLabelNew.visibility = View.VISIBLE
        }else{
            itemView.ivLabelNew.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        itemClick.click(adapterPosition)
    }
}