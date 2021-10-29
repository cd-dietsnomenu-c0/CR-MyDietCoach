package com.diets.weightloss.common.db.entities

import java.io.Serializable


data class HistoryDiet(var index: Int,
                       var dietNumber: Int,
                       var startTime: Long,
                       var endTime: Long,
                       var state: Int,
                       var difficulty: Int,
                       var loseLifes: Int,
                       var userDifficulty: Int,
                       var satisfaction: Int,
                       var comment: String,
                       var imageUrl: String,
                       var name: String,
                       var readableStart: String,
                       var readableEnd: String,
                       var readablePeriod: Int,
                       var weightUntil: Float,
                       var weightAfter: Float) : Serializable {
}