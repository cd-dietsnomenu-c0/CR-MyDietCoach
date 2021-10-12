package com.diets.weightloss.presentation.history.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.DietHistory
import kotlinx.android.synthetic.main.history_diet_vh.view.*

class HistoryDietVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.history_diet_vh, viewGroup, false)) {


    fun bind(dietHistory: DietHistory) {
        Glide.with(itemView.context).load(dietHistory.imageUrl).into(itemView.ivImage)
        itemView.tvName.text = dietHistory.name
        itemView.tvTime.text = dietHistory.readablePeriod

        if (dietHistory.state == Const.UNCOMPLETED_DIET){
            itemView.tvState.background = itemView.resources.getDrawable(R.drawable.shape_history_uncomplete)
            itemView.tvState.text = itemView.resources.getString(R.string.uncompleted_history)
        }else{
            itemView.tvState.background = itemView.resources.getDrawable(R.drawable.shape_history_complete)
            itemView.tvState.text = itemView.resources.getString(R.string.completed_history)
        }


    }
}