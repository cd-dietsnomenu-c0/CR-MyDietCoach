package com.jundev.weightloss.utils.ad

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.jundev.weightloss.Config
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.analytics.Ampl

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var inter: InterstitialAd? = null
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var isFailedLoad = false
    var adsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var bufferAdsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var adLoader: AdLoader? = null
    var nativeSpeaker: NativeSpeaker? = null
    var isNeedShowNow = false


    fun init(context: Context) {
        inter = InterstitialAd(context)
        inter?.adUnitId = context.getString(R.string.interstitial_id)
        inter?.loadAd(AdRequest.Builder().build())
        loadNative(context)
        inter?.adListener = object : AdListener() {

            override fun onAdFailedToLoad(p0: Int) {
                Ampl.failedOneLoads()
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    Ampl.failedAllLoads()
                    isFailedLoad = true
                }
            }

            override fun onAdClosed() {
                inter?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                if (isNeedShowNow){
                    isNeedShowNow = false
                    inter?.show()
                    Ampl.showAd()
                }
            }
        }
    }

    private fun loadNative(context: Context) {
        adLoader = AdLoader
                .Builder(context, context.getString(R.string.native_ad))
                .forUnifiedNativeAd { nativeAD ->
                    bufferAdsList.add(nativeAD)
                    if (!adLoader!!.isLoading) {
                        endLoading()
                    }
                }.withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(p0: Int) {
                        if (!adLoader!!.isLoading) {
                            endLoading()
                        }
                    }
                }).build()
        adLoader?.loadAds(AdRequest.Builder().build(), Config.NATIVE_ITEMS_MAX)
    }

    private fun endLoading() {
        if (bufferAdsList.size > 0) {
            adsList = bufferAdsList
            bufferAdsList = arrayListOf()
            nativeSpeaker?.loadFin(adsList)
        }
    }

    fun observeOnNativeList(nativeSpeaker: NativeSpeaker) {
        if (adsList.size > 0) {
            nativeSpeaker.loadFin(adsList)
        } else {
            this.nativeSpeaker = nativeSpeaker
        }
    }

    fun refreshNativeAd(context: Context) {
        nativeSpeaker = null
        loadNative(context)
    }

    private fun reload() {
        inter?.loadAd(AdRequest.Builder().build())
    }

    fun checkLoad() {
        if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInter() {
        if (Counter.getInstance().getCounter() % MAX_REQUEST_AD == 0) {
            if (inter?.isLoaded == true) {
                inter?.show()
                Ampl.showAd()
                Counter.getInstance().adToCounter()
            } else if (isFailedLoad) {
                counterFailed = 0
                isFailedLoad = false
                reload()
            }
        } else {
            Counter.getInstance().adToCounter()
        }
    }

    fun getShow(){
        if (inter?.isLoaded == true){
            inter?.show()
        }else{
            isNeedShowNow = true
        }
    }
}