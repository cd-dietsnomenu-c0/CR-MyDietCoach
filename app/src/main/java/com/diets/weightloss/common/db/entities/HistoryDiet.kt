package com.diets.weightloss.common.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.diets.weightloss.model.interactive.Diet
import java.io.Serializable

@Entity
data class HistoryDiet(@PrimaryKey(autoGenerate = true) var id: Long,
                       var dietNumber: Int,
                       var startTime: Long,
                       var endTime: Long,
                       var state: Int,
                       var difficulty: Int,
                       var loseLifes: Int,
                       var userDifficulty: Int,
                       var satisfaction: Int,
                       var comment: String,
                       @Ignore
                       var imageUrl: String,
                       @Ignore
                       var name: String,
                       @Ignore
                       var readableStart: String,
                       @Ignore
                       var readableEnd: String,
                       @Ignore
                       var readablePeriod: Int,
                       var weightUntil: Float,
                       var weightAfter: Float) : Serializable {

    constructor() : this(0, 0, 0, 0,
            0, 0, 0, 0, 0,
            "", "", "", "", "", 0, 0f, 0f)
}