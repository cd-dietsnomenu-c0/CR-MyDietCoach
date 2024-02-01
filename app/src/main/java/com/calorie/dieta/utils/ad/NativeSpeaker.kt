package com.calorie.dieta.utils.ad

import com.google.android.gms.ads.nativead.NativeAd

interface NativeSpeaker {
    fun loadFin(nativeList : ArrayList<NativeAd>)
}