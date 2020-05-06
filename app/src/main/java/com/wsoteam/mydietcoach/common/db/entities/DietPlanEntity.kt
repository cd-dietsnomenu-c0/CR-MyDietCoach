package com.wsoteam.mydietcoach.common.db.entities

import androidx.room.*
import com.wsoteam.mydietcoach.POJOS.interactive.Diet

@Entity
data class DietPlanEntity(@PrimaryKey var id: Int, var timeTrigger : Long,
                          var name: String, var isCompleted: Boolean,
                          var isNeedNotification: Boolean, var currentDay : Int,
                          var missingDays : Int,
                          var difficulty : Int, var breakfastState : Int,
                          var lunchState : Int, var dinnerState : Int,
                          var snakeState : Int, var snake2State : Int) {

    constructor(diet: Diet, difficulty: Int, timeInMillis : Long) : this (0, timeInMillis,  diet.title, false,
            true, 0, 0, difficulty, 0,
            0, 0,   0, 0)
}