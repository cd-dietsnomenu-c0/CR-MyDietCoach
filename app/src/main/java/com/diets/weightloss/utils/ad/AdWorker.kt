package com.diets.weightloss.utils.ad

import android.content.Context
import android.util.Log
import com.diets.weightloss.App
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.weightloss.Config
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.utils.analytics.FBAnalytic
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import kotlin.random.Random

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var inter: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var isFailedLoad = false
    var adsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var bufferAdsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var adLoader: AdLoader? = null
    var nativeSpeaker: NativeSpeaker? = null
    var isNeedShowNow = false
    private var counterRewardFailed = 0
    private const val MAX_QUERY_REWARD_VIDEO = 3

    init {
        FrequencyManager.runSetup()
    }


    fun init(context: Context) {
        inter = InterstitialAd(context)
        inter?.adUnitId = context.getString(R.string.interstitial_id)
        inter?.loadAd(AdRequest.Builder().build())
        loadReward()
        loadNative(context)
        inter?.adListener = object : AdListener() {

            override fun onAdFailedToLoad(p0: Int) {
                Ampl.failedOneLoads(p0)
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    Ampl.failedAllLoads(p0)
                    isFailedLoad = true
                }
            }

            override fun onAdClosed() {
                inter?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                if (isNeedShowNow && needShow() && !Config.FOR_TEST) {
                    isNeedShowNow = false
                    inter?.show()
                    Ampl.showAd()
                }
            }

            override fun onAdClicked() {
                super.onAdClicked()
                FBAnalytic.adClick()
            }
        }
    }

    private fun loadNative(context: Context) {
        if (!Config.FOR_TEST) {
            if (!PreferenceProvider.isHasPremium) {
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
        }
    }

    fun loadReward() {
        var context = App.getInstance()
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
                })
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
            return Random.nextInt(100) <= PreferenceProvider.frequencyPercent
        }
    }
}