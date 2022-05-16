package com.diets.weightloss.presentation.diets.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.weightloss.R
import com.diets.weightloss.utils.analytics.Ampl
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import kotlinx.android.synthetic.main.vh_ad_types.view.*

class ADVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.vh_ad_types, viewGroup, false)) {

    private var nativeAdViewBinder: NativeAdViewBinder? = null

    init {
        nativeAdViewBinder = NativeAdViewBinder
                .Builder(itemView.ad_view)
                .setCallToActionView(itemView.ad_call_to_action)
                .setIconView(itemView.ad_icon)
                .setMediaView(itemView.ad_media)
                .setTitleView(itemView.ad_headline)
                .setSponsoredView(itemView.ad_body)
                .setWarningView(itemView.ad_warning)
                .build()
    }

    fun bind(unifiedNativeAd: NativeAd) {
        bindAdView(unifiedNativeAd)
    }

    private fun bindAdView(nativeAd: NativeAd) {
        try {
            nativeAd.bindNativeAd(nativeAdViewBinder!!)
            //itemView.flAdContainer.visibility = View.VISIBLE
        } catch (ex: Exception) {
            Ampl.errorNative(ex.toString())
        }
    }
}