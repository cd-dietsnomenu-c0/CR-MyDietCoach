package com.wsoteam.mydietcoach.ad

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.wsoteam.mydietcoach.R

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var countRequestAd: Int = 0
    private var inter : InterstitialAd? = null

    fun init(context: Context){
        inter = InterstitialAd(context)
        inter?.adUnitId = context.getString(R.string.interstitial_id)
        inter?.loadAd(AdRequest.Builder().build())
        inter?.adListener = object : AdListener() {

            override fun onAdFailedToLoad(p0: Int) {
                if (countRequestAd <= MAX_REQUEST_AD){
                    inter?.loadAd(AdRequest.Builder().build())
                }
            }

            override fun onAdClosed() {
                inter?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                Log.e("LOL", "onAdLoaded")
                super.onAdLoaded()
            }
        }
    }

    fun showInter(){
        if (Counter.getInstance().getCounter() % 3 == 0) {
            if (inter?.isLoaded == true) {
                inter?.show()
                Counter.getInstance().adToCounter()
            } else {
                //TODO log
            }
        }else{
            Counter.getInstance().adToCounter()
        }
    }
}