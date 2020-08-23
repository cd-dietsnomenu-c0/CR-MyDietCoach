package com.jundev.weightloss.diets.list.modern.controllers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.jundev.weightloss.Config
import com.jundev.weightloss.POJOS.interactive.AllDiets
import com.jundev.weightloss.diets.list.ItemClick

class InteractiveAdapter(val allDiets: AllDiets, var itemClick: ItemClick, var nativeList: ArrayList<UnifiedNativeAd>, val typeName: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE = 0
    val AD_TYPE = 1
    var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            ITEM_TYPE -> InteractiveVH(inflater, parent, object : ItemClick {
                override fun click(position: Int) {
                    itemClick.click(getRealPosition(position))
                }

                override fun newDietsClick() {
                }
            })
            AD_TYPE -> NativeVH(inflater, parent)
            else -> InteractiveVH(inflater, parent, itemClick)
        }

    }

    override fun getItemCount(): Int {
        return if(nativeList.isNotEmpty()){
            allDiets.dietList.size + allDiets.dietList.size / (Config.WHICH_AD_NEW_DIETS - 1)
        }else{
            return allDiets.dietList.size
        }
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
            ITEM_TYPE ->(holder as InteractiveVH).bind(allDiets.dietList[getRealPosition(position)].title,
                    allDiets.dietList[getRealPosition(position)].mainImage, allDiets.dietList[getRealPosition(position)].isNew,
                    allDiets.dietList[getRealPosition(position)].shortIntroduction, allDiets.dietList[getRealPosition(position)].days.size,
                    typeName, allDiets.dietList[getRealPosition(position)].kcal)
            AD_TYPE ->(holder as NativeVH).bind(nativeList[getAdPosition()])
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (nativeList.isEmpty()){
            position
        }else{
           position - position / Config.WHICH_AD_NEW_DIETS
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        Log.e("LOL", listAds.size.toString())
        nativeList = listAds
        notifyDataSetChanged()
    }

    private fun getAdPosition() : Int{
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
}