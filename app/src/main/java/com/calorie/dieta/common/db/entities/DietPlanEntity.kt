package com.calorie.dieta.common.db.entities

import androidx.room.*
import com.calorie.dieta.model.interactive.Diet
import com.calorie.dieta.common.db.DayLoseConverter

const val DEFAULT_WEIGHT_UNTIL = 0f
const val SKIP_WEIGHT_UNTIL = -1f

const val EASY_LEVEL = 2
const val NORMAL_LEVEL = 1
const val HARD_LEVEL = 0

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

