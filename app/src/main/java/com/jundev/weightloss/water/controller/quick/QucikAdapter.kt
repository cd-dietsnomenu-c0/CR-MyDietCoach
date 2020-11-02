package com.jundev.weightloss.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QucikAdapter() : RecyclerView.Adapter<QuickVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickVH {
        val inflater = LayoutInflater.from(parent.context)
        return QuickVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: QuickVH, position: Int) {
    }
}