package com.calorie.dieta.presentation.diets.list.modern.article.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calorie.dieta.R
import kotlinx.android.synthetic.main.vh_results.view.*

class ResultsVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.vh_results, viewGroup, false)) {
    fun bind(resultText: String) {
        itemView.tvTextResults.text = resultText
    }
}