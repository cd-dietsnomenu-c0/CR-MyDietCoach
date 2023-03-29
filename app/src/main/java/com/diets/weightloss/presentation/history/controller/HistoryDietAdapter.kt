package com.diets.weightloss.presentation.history.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd

private const val NATIVE_AD_PERIOD_INSERT = 6

class HistoryDietAdapter(val listDiet: List<HistoryDiet>, val clickListener: HistoryClickListener, var nativeList: ArrayList<NativeAd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val BODY_TYPE = 0
    val AD_TYPE = 1
    var counter = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BODY_TYPE -> HistoryDietVH(inflater, parent, object : HistoryClickListener{
                override fun onClick(position: Int) {
                    clickListener.onClick(getDietHistoryPosition(position))
                }
            })
            else -> ADVH(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            BODY_TYPE -> (holder as HistoryDietVH).bind(listDiet[getDietHistoryPosition(position)])
            AD_TYPE -> (holder as ADVH).bind(nativeList[getAdPosition()])
        }

    }

    fun insertAds(listAds: ArrayList<NativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
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

    private fun getDietHistoryPosition(position: Int): Int {
        return if (nativeList.isEmpty()) {
            position
        } else {
            position - position / NATIVE_AD_PERIOD_INSERT
        }
    }

    override fun getItemCount(): Int {
        return if (nativeList.isNotEmpty()) {
            listDiet.size + listDiet.size / NATIVE_AD_PERIOD_INSERT
        } else {
            listDiet.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % NATIVE_AD_PERIOD_INSERT == 0 && position > 0 && nativeList.isNotEmpty()) {
            AD_TYPE
        } else {
            BODY_TYPE
        }
    }
}