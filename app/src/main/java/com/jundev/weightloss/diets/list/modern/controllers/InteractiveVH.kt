package com.jundev.weightloss.diets.list.modern.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundev.weightloss.R
import com.jundev.weightloss.diets.list.ItemClick
import kotlinx.android.synthetic.main.interactive_vh.view.*

class InteractiveVH(inflater: LayoutInflater, viewGroup: ViewGroup, val itemClick: ItemClick) : RecyclerView.ViewHolder(inflater.inflate(R.layout.interactive_vh, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(title: String, mainImage: String, isNew: Boolean, shortIntroduction: String, size: Int, typeName: String) {
        Glide.with(itemView.context).load(mainImage).into(itemView.ivBackground)
        itemView.tvTitle.text = title
        itemView.tvSubTitle.text = shortIntroduction
        itemView.tvCountDays.text = itemView.resources.getQuantityString(R.plurals.days_plur, size, size)
        itemView.tvType.text = typeName
    }

    override fun onClick(p0: View?) {
        itemClick.click(adapterPosition)
    }
}