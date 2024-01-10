package com.meal.planner.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meal.planner.model.interactive.DietDay
import com.meal.planner.R
import com.meal.planner.presentation.diets.list.modern.article.controller.days.DietDayAdapter
import kotlinx.android.synthetic.main.vh_menu.view.*

class MenuVH(inflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_menu, viewGroup, false)) {

    init {
        itemView.rvDietDays.layoutManager = LinearLayoutManager(itemView.context)
    }
    fun bind(menuTitle: String, menuText: String, days: List<DietDay>, hintText: String) {
        itemView.tvHintText.text = hintText
        itemView.tvDietPlanTitle.text = menuTitle
        itemView.tvTextDietPlan.text = menuText
        itemView.rvDietDays.adapter = DietDayAdapter(days)
    }
}
