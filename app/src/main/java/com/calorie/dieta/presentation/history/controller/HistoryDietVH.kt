package com.calorie.dieta.presentation.history.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calorie.dieta.R
import com.calorie.dieta.common.db.entities.COMPLETED_DIET
import com.calorie.dieta.common.db.entities.HistoryDiet
import com.calorie.dieta.common.db.entities.LOSE_DIET
import kotlinx.android.synthetic.main.history_diet_vh.view.*

class HistoryDietVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val clickListener: HistoryClickListener)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.history_diet_vh, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clickListener.onClick(adapterPosition)
    }

    fun bind(historyDiet: HistoryDiet) {
        Glide.with(itemView.context).load(historyDiet.imageUrl).into(itemView.ivImage)
        itemView.tvName.text = historyDiet.name
        if (historyDiet.startTime != 0L) {
            itemView.tvTime.text = "${historyDiet.readableStart} - ${historyDiet.readableEnd}"
        }else{
            itemView.tvTime.visibility = View.GONE
        }
        when (historyDiet.state) {
            LOSE_DIET -> {
                itemView.tvState.background = itemView.resources.getDrawable(R.drawable.shape_history_uncomplete)
                itemView.tvState.text = itemView.resources.getString(R.string.uncompleted_history)
            }
            COMPLETED_DIET -> {
                itemView.tvState.background = itemView.resources.getDrawable(R.drawable.shape_history_complete)
                itemView.tvState.text = itemView.resources.getString(R.string.completed_history)
            }
            /*else -> {
                itemView.tvState.background = itemView.resources.getDrawable(R.drawable.shape_history_break)
                itemView.tvState.text = itemView.resources.getString(R.string.break_history)
            }*/
        }
    }
}