package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.model.water.WaterMarathon
import com.diets.weightloss.presentation.diets.controller.ADVH
import com.diets.weightloss.presentation.diets.list.modern.controllers.NativeVH
import com.yandex.mobile.ads.nativeads.NativeAd
import java.lang.Exception

class MarathonAdapter(val list: List<WaterMarathon>, var nativeList: ArrayList<NativeAd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var counter = 0

    companion object {
        const val FIRST_PLACE_TYPE = 0
        const val SECOND_PLACE_TYPE = 1
        const val THIRD_PLACE_TYPE = 2
        const val OTHER_PLACE_TYPE = 3
        const val AD_TYPE = 4
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FIRST_PLACE_TYPE -> FirstPlaceVH(inflater, parent)
            SECOND_PLACE_TYPE -> SecondPlaceVH(inflater, parent)
            THIRD_PLACE_TYPE -> ThirdPlaceVH(inflater, parent)
            OTHER_PLACE_TYPE -> OtherPlaceVH(inflater, parent)
            AD_TYPE -> NativeWaterVH(inflater, parent)
            else -> SecondPlaceVH(inflater, parent)
        }
    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (nativeList.isEmpty()) {
            list.size
        } else {
            if (list.size <= 2){
                list.size + 1
            }else{
                list.size + 2
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            FIRST_PLACE_TYPE -> (holder as FirstPlaceVH).bind(list[position])
            SECOND_PLACE_TYPE -> (holder as SecondPlaceVH).bind(list[1])
            THIRD_PLACE_TYPE -> (holder as ThirdPlaceVH).bind(list[2])
            OTHER_PLACE_TYPE -> (holder as OtherPlaceVH).bind(list[getOtherPosition(position)])
            AD_TYPE -> (holder as ADVH).bind(nativeList[getAdPosition()])
            else -> Exception()
        }
    }

    private fun getOtherPosition(position: Int): Int {
        return if (nativeList.isEmpty()) {
            position
        } else {
            position - 2
        }
    }


    private fun getAdPosition(): Int {
        var position = 0
        if (counter > nativeList.size - 1) {
            position = 0
            counter = 1
        } else {
            position = counter
            counter++
        }
        return position
    }

    override fun getItemViewType(position: Int): Int {
        if (nativeList.isEmpty()) {
            return when (position) {
                0 -> FIRST_PLACE_TYPE
                1 -> SECOND_PLACE_TYPE
                2 -> THIRD_PLACE_TYPE
                else -> OTHER_PLACE_TYPE
            }
        } else {
            return when (position) {
                0 -> FIRST_PLACE_TYPE
                1 -> AD_TYPE
                2 -> SECOND_PLACE_TYPE
                3 -> THIRD_PLACE_TYPE
                4 -> AD_TYPE
                else -> OTHER_PLACE_TYPE
            }
        }

    }
}