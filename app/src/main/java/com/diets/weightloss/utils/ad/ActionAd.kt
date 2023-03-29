package com.diets.weightloss.utils.ad

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.diets.weightloss.Config
import com.diets.weightloss.utils.PreferenceProvider

object ActionAd {

    private const val ACTION_TRIGGER = 5

    fun action(activity: Activity) {
        var actionNumber = PreferenceProvider.actionNumber
        actionNumber ++

        if (actionNumber >= ACTION_TRIGGER){
            if (!Config.FOR_TEST) {
                AdWorker.showInterWithoutCounter(activity)
            }
            actionNumber = 0
        }

        PreferenceProvider.actionNumber = actionNumber
    }

}