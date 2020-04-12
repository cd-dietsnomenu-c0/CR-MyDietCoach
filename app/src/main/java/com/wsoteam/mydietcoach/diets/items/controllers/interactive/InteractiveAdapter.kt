package com.wsoteam.mydietcoach.diets.items.controllers.interactive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.AllDiets
import com.wsoteam.mydietcoach.ad.controller.NativeVH
import com.wsoteam.mydietcoach.diets.ItemClick
import com.wsoteam.mydietcoach.diets.items.controllers.ItemVH

class InteractiveAdapter(val allDiets: AllDiets, var itemClick: ItemClick, var nativeList : ArrayList<UnifiedNativeAd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE = 0
    val AD_TYPE = 1
    var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            ITEM_TYPE ->  InteractiveVH(inflater, parent, itemClick)
            AD_TYPE -> NativeVH(inflater, parent)
            else ->  InteractiveVH(inflater, parent, itemClick)
        }

    }

    override fun getItemCount(): Int {
        return allDiets.dietList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % Config.WHICH_AD_NEW_DIETS == 0 && position > 0 && nativeList.isNotEmpty()) {
            AD_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM_TYPE ->(holder as InteractiveVH).bind(allDiets.dietList[position].title, allDiets.dietList[position].mainImage)
            AD_TYPE ->(holder as NativeVH).bind(nativeList[getAdPosition()])
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }

    private fun getAdPosition() : Int{
        var position = 0
        if (counter > Config.NATIVE_ITEMS_MAX - 1){
            position = 0
            counter = 1
        }else{
            position = counter
            counter++
        }
        return position
    }
}