package com.diets.weightloss.utils.analytics

import com.diets.weightloss.App
import com.google.firebase.analytics.FirebaseAnalytics

object FBAnalytic {

    fun trial(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("trial", null)
    }

    fun adShow(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("ad_show", null)
    }

    fun adClick(){
        FirebaseAnalytics.getInstance(App.getContext()).logEvent("ad_click", null)
    }


}