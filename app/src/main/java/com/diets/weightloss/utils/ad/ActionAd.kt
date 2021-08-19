package com.diets.weightloss.utils.ad

import com.diets.weightloss.Config
import com.diets.weightloss.utils.PreferenceProvider

object ActionAd {

    private const val ACTION_TRIGGER = 5

    fun action(){
        var actionNumber = PreferenceProvider.actionNumber
        actionNumber ++

        if (actionNumber >= ACTION_TRIGGER){
            if (!Config.FOR_TEST) {
                AdWorker.showInterWithoutCounter()
            }
            actionNumber = 0
        }

        PreferenceProvider.actionNumber = actionNumber
    }

}