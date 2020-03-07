package com.wsoteam.mydietcoach.diets.items.article.interactive.controller.inside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.Eat

class EatAdapter(var eatList: List<Eat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val BREAKFAST = 0
    val LUNCH = 1
    val DINNER = 2
    val SNACK = 3
    val SNACK_SECOND = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BREAKFAST -> BreakfastVH(inflater, parent)
            LUNCH -> LunchVH(inflater, parent)
            DINNER -> DinnerVH(inflater, parent)
            SNACK -> SnackVH(inflater, parent)
            SNACK_SECOND -> SecondSnackVH(inflater, parent)
            else -> BreakfastVH(inflater, parent)
        }
    }

    override fun getItemCount(): Int {
        return eatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BreakfastVH -> {
                (holder as BreakfastVH).bind(eatList[position])
            }
            is LunchVH -> {
                (holder as LunchVH).bind(eatList[position])
            }
            is DinnerVH -> {
                (holder as DinnerVH).bind(eatList[position])
            }
            is SnackVH -> {
                (holder as SnackVH).bind(eatList[position])
            }
            is SecondSnackVH -> {
                (holder as SecondSnackVH).bind(eatList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var type = 0
        when (eatList[position].type) {
            BREAKFAST -> type = BREAKFAST
            LUNCH -> type = LUNCH
            DINNER -> type = DINNER
            SNACK -> type = SNACK
            SNACK_SECOND -> type = SNACK_SECOND
        }
        return type
    }
}