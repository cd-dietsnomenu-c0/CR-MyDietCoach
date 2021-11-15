package com.diets.weightloss.common.db.entities

import androidx.room.*
import com.diets.weightloss.model.interactive.Diet
import com.diets.weightloss.common.db.DayLoseConverter

const val DEFAULT_WEIGHT_UNTIL = 0f
const val SKIP_WEIGHT_UNTIL = -1f

@Entity
@TypeConverters(DayLoseConverter::class)
data class DietPlanEntity(
        @PrimaryKey
        var id: Int,
        var timeTrigger: Long,
        var name: String,
        var isCompleted: Boolean,
        var isNeedNotification: Boolean,
        var currentDay: Int,
        var missingDays: Int,
        var difficulty: Int,
        var breakfastState: Int,
        var lunchState: Int,
        var dinnerState: Int,
        var snakeState: Int,
        var snake2State: Int,
        var numbersLosesDays: MutableList<Int>,

        var dietNumber: Int,
        var startTime: Long,
        var weightUntil: Float
) {

    constructor(diet: Diet, difficulty: Int, timeInMillis: Long, startTime : Long) : this(0, timeInMillis, diet.title, false,
            true, 0, 0, difficulty, 0,
            0, 0, 0, 0, mutableListOf<Int>(), diet.index, startTime, DEFAULT_WEIGHT_UNTIL)
}

