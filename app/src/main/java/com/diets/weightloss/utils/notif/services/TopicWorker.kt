package com.diets.weightloss.utils.notif.services

import com.diets.weightloss.Config
import com.google.firebase.messaging.FirebaseMessaging

object TopicWorker {

    fun changeWaterNotifState(isOn : Boolean){
        if (isOn){
            FirebaseMessaging.getInstance().subscribeToTopic(Config.WATER_TOPIC).addOnSuccessListener { }
        }else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.WATER_TOPIC).addOnSuccessListener { }
        }
    }
}