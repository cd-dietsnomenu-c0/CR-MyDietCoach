package com.meal.planner.utils.notif.services

import com.meal.planner.Config
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