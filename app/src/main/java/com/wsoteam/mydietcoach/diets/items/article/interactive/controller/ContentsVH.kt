package com.wsoteam.mydietcoach.diets.items.article.interactive.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.contents_vh.view.*

class ContentsVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.contents_vh, viewGroup, false)) {

    fun bind(iContents: IContents) {
        itemView.tvDescription.setOnClickListener {
            iContents.moveTo(2)
        }
        itemView.tvBenefits.setOnClickListener {
            iContents.moveTo(3)
        }
        itemView.tvCons.setOnClickListener {
            iContents.moveTo(4)
        }
        itemView.tvMenu.setOnClickListener {
            iContents.moveTo(5)
        }
        itemView.tvResults.setOnClickListener {
            iContents.moveTo(6)
        }
        itemView.tvReviews.setOnClickListener {
            iContents.moveTo(7)
        }
    }
}