package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency.controller

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FrequencyAdapter(val pieImgIndexes : ArrayList<Int>, val pieNames : ArrayList<String>, val piePercents : ArrayList<Float>, val dividerTexts : Array<String>, val centerPieText : SpannableString) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val PIE_TYPE = 0
        private const val DIVIDER_TYPE = 1
        private const val FREQUNECY_DETAIL_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            PIE_TYPE -> PieChartVH(inflater, parent)
            DIVIDER_TYPE -> DividerVH(inflater, parent)
            FREQUNECY_DETAIL_TYPE -> FrequencyDetailsVH(inflater, parent)
            else -> PieChartVH(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            PIE_TYPE -> (holder as PieChartVH).onBind(pieImgIndexes, pieNames, piePercents, centerPieText)
            DIVIDER_TYPE -> (holder as DividerVH).onBind(dividerTexts[getDividerPosition(position)])
            FREQUNECY_DETAIL_TYPE -> (holder as FrequencyDetailsVH).onBind()
        }
    }

    private fun getDividerPosition(position: Int): Int {
        return when(position){
            2 -> 0
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1){
            DIVIDER_TYPE
        }else if(position == 0){
            PIE_TYPE
        } else{
            FREQUNECY_DETAIL_TYPE
        }
    }
}