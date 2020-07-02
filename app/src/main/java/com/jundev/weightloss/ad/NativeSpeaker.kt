package com.jundev.weightloss.ad

import com.google.android.gms.ads.formats.UnifiedNativeAd

interface NativeSpeaker {
    fun loadFin(nativeList : ArrayList<UnifiedNativeAd>)
}