package com.wsoteam.mydietcoach.diets.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.vh_native.view.*

class AdVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.vh_native, viewGroup, false)) {

    init {
        itemView.ad_view.mediaView = itemView.ad_media
        itemView.ad_view.headlineView = itemView.ad_headline
        itemView.ad_view.bodyView = itemView.ad_body
        itemView.ad_view.callToActionView = itemView.ad_call_to_action
        itemView.ad_view.iconView = itemView.ad_icon
        itemView.ad_view.starRatingView = itemView.ad_stars
    }

    fun bind(unifiedNativeAd: UnifiedNativeAd) {
        bindAdView(unifiedNativeAd)
    }

    private fun bindAdView(nativeAd: UnifiedNativeAd){
        (itemView.ad_view.headlineView as TextView).text = nativeAd.headline
        (itemView.ad_view.bodyView as TextView).text = nativeAd.body
        (itemView.ad_view.callToActionView as Button).text = nativeAd.callToAction

        val icon = nativeAd.icon

        if (icon == null) {
            itemView.ad_view.iconView.visibility = View.INVISIBLE
        } else {
            (itemView.ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            itemView.ad_view.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.starRating == null) {
            itemView.ad_view.starRatingView.visibility = View.INVISIBLE
        } else {
            (itemView.ad_view.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            itemView.ad_view.starRatingView.visibility = View.VISIBLE
        }



        itemView.ad_view.setNativeAd(nativeAd)
    }
}