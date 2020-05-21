package com.wsoteam.mydietcoach.diets.controllers

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.diets.ItemClick
import java.util.ArrayList

class SectionAdapter(var sectionList: ArrayList<Section>, var leftDrawables: Array<String>, var itemClick: ItemClick,
                     var nativeList : ArrayList<UnifiedNativeAd>) : Adapter<RecyclerView.ViewHolder>() {
    val HEAD_TYPE = 0
    val BODY_TYPE = 1
    val AD_TYPE = 2
    val ad_count = 2
    var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            HEAD_TYPE -> HeadVH(inflater, parent, itemClick)
            AD_TYPE -> AdVH(inflater, parent)
            else -> SectionVH(inflater, parent, object : ItemClick{
                override fun click(position: Int) {
                    itemClick.click(getNumber(position))
                }

                override fun newDietsClick() {

                }
            })
        }
    }

    override fun getItemCount(): Int {
        return if (nativeList.isEmpty()) {
            sectionList.size + 1
        }else{
            sectionList.size + 1 + ad_count
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
             HEAD_TYPE -> (holder as HeadVH).bind()
             BODY_TYPE -> (holder as SectionVH).bind(sectionList[getNumber(position)], leftDrawables[sectionList[getNumber(position)].urlOfImage.toInt()])
             AD_TYPE -> (holder as AdVH).bind(nativeList[getAdNumber()])
        }
    }

    private fun getAdNumber(): Int {
        var position = 0
        if (counter > nativeList.size - 1){
            position = 0
            counter = 1
        }else{
            position = counter
            counter++
        }
        return position
    }

    private fun getNumber(position: Int): Int {
        return if (nativeList.isEmpty() || position == 1){
            position - 1
        }else if (position in 3..6){
            position - 2
        }else{
            position - 3
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            HEAD_TYPE
        }else if (nativeList.isEmpty() || position == 1){
            return BODY_TYPE
        }else if (position == 2 || position == 7){
            AD_TYPE
        }else {
            BODY_TYPE
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }
}