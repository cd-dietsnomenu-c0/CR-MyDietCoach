package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.model.water.WaterMarathon
import java.lang.Exception

class MarathonAdapter(val list: List<WaterMarathon>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FIRST_PLACE_TYPE = 0
        const val SECOND_PLACE_TYPE = 1
        const val THIRD_PLACE_TYPE = 2
        const val OTHER_PLACE_TYPE = 3
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FIRST_PLACE_TYPE -> FirstPlaceVH(inflater, parent)
            SECOND_PLACE_TYPE -> SecondPlaceVH(inflater, parent)
            THIRD_PLACE_TYPE -> ThirdPlaceVH(inflater, parent)
            OTHER_PLACE_TYPE -> OtherPlaceVH(inflater, parent)
            else -> SecondPlaceVH(inflater, parent)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 5
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            FIRST_PLACE_TYPE -> (holder as FirstPlaceVH).bind(list[position])
            SECOND_PLACE_TYPE -> (holder as SecondPlaceVH).bind(list[position])
            THIRD_PLACE_TYPE -> (holder as ThirdPlaceVH).bind(list[position])
            OTHER_PLACE_TYPE -> (holder as OtherPlaceVH).bind(list[2])
            else -> Exception()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> FIRST_PLACE_TYPE
            1 -> SECOND_PLACE_TYPE
            2 -> THIRD_PLACE_TYPE
            else -> OTHER_PLACE_TYPE
        }
    }
}