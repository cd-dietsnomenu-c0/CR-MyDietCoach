package com.diets.weightloss.presentation.water.stats.pager.pages.frequency.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI

class FrequencyAdapter(val pieImgIndexes : ArrayList<Int>, val pieNames : ArrayList<String>, val piePercents : ArrayList<Float>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val PIE_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            PIE_TYPE -> PieChartVH(inflater, parent)
            else -> PieChartVH(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            PIE_TYPE -> (holder as PieChartVH).onBind(pieImgIndexes, pieNames, piePercents)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}