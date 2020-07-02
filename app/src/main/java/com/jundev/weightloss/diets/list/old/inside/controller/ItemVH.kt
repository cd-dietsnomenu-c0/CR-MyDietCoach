package com.jundev.weightloss.diets.list.old.inside.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundev.weightloss.POJOS.Subsection
import com.jundev.weightloss.R
import com.jundev.weightloss.diets.list.ItemClick
import kotlinx.android.synthetic.main.item_of_subsections.view.*

class ItemVH(inflater: LayoutInflater, viewGroup: ViewGroup, var itemClick: ItemClick)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_of_subsections, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        itemClick.click(adapterPosition)
    }

    fun bind(subsection: Subsection, resourceId: String) {
        itemView.tvSubsections.text = subsection.description
        Glide.with(itemView.context).load(resourceId).into(itemView.ivSubsections)
    }
}