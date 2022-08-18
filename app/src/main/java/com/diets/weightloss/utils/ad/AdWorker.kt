package com.diets.weightloss.utils.ad

import android.content.Context
import android.util.Log
import com.diets.weightloss.App
import com.diets.weightloss.Config
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.utils.analytics.FBAnalytic
import com.google.android.gms.ads.*
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoader
import com.yandex.mobile.ads.rewarded.RewardedAd
import java.util.*

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var inter: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var isFailedLoad = false
    var adsList: ArrayList<NativeAd> = arrayListOf()
    var bufferAdsList: ArrayList<NativeAd> = arrayListOf()
    var adLoader: NativeBulkAdLoader? = null
    var nativeSpeaker: NativeSpeaker? = null
    var isNeedShowNow = false
    private var counterRewardFailed = 0
    private const val MAX_QUERY_REWARD_VIDEO = 3

    private val MAX_REQUEST_NATIVE_AD = 3
    private var nativeAdRequestCounter = 0

    init {
        FrequencyManager.runSetup()
    }


    fun init(context: Context) {
        inter = InterstitialAd(context)
        inter?.setAdUnitId(context.getString(R.string.inter_id))
        inter?.loadAd(AdRequest.Builder().build())
        loadNative()
        inter?.setInterstitialAdEventListener(object : InterstitialAdEventListener {

            override fun onAdFailedToLoad(p0: AdRequestError) {
                //Ampl.failedOneLoads()
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    //Ampl.failedAllLoads()
                    isFailedLoad = true
                }
            }

            override fun onAdShown() {
            }

            override fun onAdDismissed() {
                inter?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdClicked() {
            }

            override fun onLeftApplication() {
            }

            override fun onReturnedToApplication() {
            }

            override fun onImpression(p0: ImpressionData?) {
            }


            override fun onAdLoaded() {
                if (isNeedShowNow && needShow()) {
                    isNeedShowNow = false
                    inter?.show()
                    Ampl.showAd()
                }
            }
        })

    }

    private fun loadNative() {
        if (!PreferenceProvider.isHasPremium) {
            adLoader = NativeBulkAdLoader(App.getContext())
            adLoader!!.setNativeBulkAdLoadListener(object : NativeBulkAdLoadListener {
                override fun onAdsLoaded(p0: MutableList<NativeAd>) {
                    if (p0.size >= Config.NATIVE_ITEMS_MAX){
                        Ampl.loadedNative()
                        bufferAdsList = ArrayList(p0)
                        endLoading()
                    }else{
                        nativeAdRequestCounter ++
                        if (nativeAdRequestCounter > MAX_REQUEST_NATIVE_AD){
                            Ampl.lessNativeAdEnd()
                            endLoading()
                        }else{
                            Ampl.lessNativeAd()
                            loadNative()
                        }
                    }
                }

                override fun onAdsFailedToLoad(p0: AdRequestError) {
                    nativeAdRequestCounter ++
                    if (nativeAdRequestCounter > MAX_REQUEST_NATIVE_AD){
                        Ampl.failLoadNativeEnd()
                        endLoading()
                    }else{
                        Ampl.failLoadNative()
                        loadNative()
                    }
                }
            })

            var config = NativeAdRequestConfiguration.Builder(App.getContext().getString(R.string.native_id)).build()
            adLoader!!.loadAds(config, Config.NATIVE_ITEMS_MAX)
        }
    }

    fun loadReward() {
        /*var context = App.getInstance()
        RewardedAd.load(
                context,
                context.resources.getString(R.string.reward_id),
                AdRequest.Builder().build(),
                object :
                        RewardedAdLoadCallback() {

                    override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                        Log.e("LOL", "fail reward")
                        counterRewardFailed++
                        if (counterRewardFailed <= MAX_QUERY_REWARD_VIDEO) {
                            loadReward()
                        }
                        rewardedAd = null
                    }

                    override fun onAdLoaded(p0: RewardedAd) {
                        Log.e("LOL", "loaded reward")
                        counterRewardFailed = 0
                        rewardedAd = p0
                    }
                })*/
    }

    fun getRewardAd() : RewardedAd?{
        return rewardedAd
    }

    private fun endLoading() {
        if (bufferAdsList.size > 0) {
            adsList = bufferAdsList
            bufferAdsList = arrayListOf()
            nativeSpeaker?.loadFin(adsList)
        }
    }

    fun observeOnNativeList(nativeSpeaker: NativeSpeaker) {
        if (!PreferenceProvider.isHasPremium) {
            if (adsList.size > 0) {
                nativeSpeaker.loadFin(adsList)
            } else {
                this.nativeSpeaker = nativeSpeaker
            }
        }
    }

    fun refreshNativeAd(context: Context) {
        /*nativeSpeaker = null
        loadNative()*/
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
        if (!PreferenceProvider.isHasPremium && needShow() && !Config.FOR_TEST) {
            if (Counter.getInstance().getCounter() % MAX_REQUEST_AD == 0) {
                if (inter?.isLoaded == true) {
                    FBAnalytic.adShow()
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
    }


    fun showInterWithoutCounter() {
        if (!PreferenceProvider.isHasPremium && needShow()) {
            if (inter?.isLoaded == true) {
                FBAnalytic.adShow()
                inter?.show()
                Ampl.showAd()
            } else if (isFailedLoad) {
                counterFailed = 0
                isFailedLoad = false
                reload()
            }
        }
    }

    fun getShow() {
        if (!PreferenceProvider.isHasPremium) {
            if (inter?.isLoaded == true && needShow()) {
                FBAnalytic.adShow()
                inter?.show()
            } else {
                isNeedShowNow = true
            }
        }
    }

    private fun needShow(): Boolean {
        if (Config.FOR_TEST) {
            return false
        } else {
            return Random().nextInt(100) <= PreferenceProvider.frequencyPercent
        }
    }
}