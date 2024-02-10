package com.calorie.dieta.presentation.water.statistics.pager.pages.marathons.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.calorie.dieta.R
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.vh_native_stat.view.*

class NativeWaterVH (layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.vh_native_stat, viewGroup, false)) {

    init {
        itemView.ad_view.mediaView = itemView.ad_media
        itemView.ad_view.headlineView = itemView.ad_headline
        itemView.ad_view.bodyView = itemView.ad_body
        itemView.ad_view.callToActionView = itemView.ad_call_to_action
        itemView.ad_view.iconView = itemView.ad_icon
    }

    fun bind(unifiedNativeAd: NativeAd) {
        bindAdView(unifiedNativeAd)
    }

    private fun bindAdView(nativeAd: NativeAd){
        (itemView.ad_view.headlineView as TextView).text = nativeAd.headline
        (itemView.ad_view.bodyView as TextView).text = nativeAd.body
        (itemView.ad_view.callToActionView as Button).text = nativeAd.callToAction
        val icon = nativeAd.icon
        if (icon == null) {
            itemView.ad_view.iconView!!.visibility = View.INVISIBLE
        } else {
            (itemView.ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            itemView.ad_view.iconView!!.visibility = View.VISIBLE
        }
        itemView.ad_view.setNativeAd(nativeAd)
    }
}