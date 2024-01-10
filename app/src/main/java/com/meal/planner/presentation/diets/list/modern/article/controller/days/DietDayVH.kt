package com.meal.planner.presentation.diets.list.modern.article.controller.days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meal.planner.model.interactive.DietDay
import com.meal.planner.R
import com.meal.planner.presentation.diets.list.modern.article.controller.days.inside.EatAdapter
import kotlinx.android.synthetic.main.item_diet_day.view.*

class DietDayVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_diet_day, viewGroup, false)) {

    init {
        itemView.rvDays.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun bind(day: DietDay) {
        itemView.tvDayTitle.text = day.title
        itemView.rvDays.adapter = EatAdapter(day.eats)
    }
}