package com.diets.weightloss.presentation.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.model.water.QuickWaterList

class QuickAdapter(val iQuick: IQuick, var waterList: QuickWaterList) : RecyclerView.Adapter<QuickVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickVH {
        val inflater = LayoutInflater.from(parent.context)
        return QuickVH(inflater, parent, iQuick)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: QuickVH, position: Int) {
        holder.bind(waterList.list[position].imgId, waterList.list[position].name, waterList.list[position].capacity)
    }

    fun setNewData(waterList: QuickWaterList, numberItem : Int){
        this.waterList = waterList
        notifyItemChanged(numberItem)
    }
}