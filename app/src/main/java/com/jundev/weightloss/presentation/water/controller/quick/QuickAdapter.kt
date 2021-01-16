package com.jundev.weightloss.presentation.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jundev.weightloss.App
import com.jundev.weightloss.R
import com.jundev.weightloss.model.water.QuickWaterList
import com.jundev.weightloss.utils.PreferenceProvider

class QuickAdapter(val iQuick: IQuick, var waterList: QuickWaterList) : RecyclerView.Adapter<QuickVH>() {

    var imgsIndexes = arrayListOf<Int>()
    var titles = arrayListOf<String>()
    var capacities = arrayListOf<String>()


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

    fun setNewData(waterList: QuickWaterList){
        this.waterList = waterList
        notifyDataSetChanged()
    }
}