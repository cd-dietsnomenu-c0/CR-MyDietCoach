package com.diets.weightloss.common

import android.util.Log
import com.diets.weightloss.App
import com.diets.weightloss.Const
import com.diets.weightloss.common.db.entities.DietPlanEntity
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.common.db.entities.SKIP_WEIGHT_UNTIL
import com.diets.weightloss.model.interactive.DietDay
import com.diets.weightloss.utils.CustomDate
import com.diets.weightloss.utils.history.HistoryProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

object DBHolder {
    var NOT_USE = -1
    var CHECKED = 1
    var NOT_CHECKED = 0
    var ONE_DAY = 86400000

    var DIET_LOSE = -1
    var DIET_CONTINUE = 0
    var DIET_COMPLETED = 1

    var NO_DIET_YET = "NO_DIET_YET"
    var INIT_DIET = "INIT_DIET"

    private var dietPlanEntity: DietPlanEntity

    init {
        dietPlanEntity = DietPlanEntity(0, 0, INIT_DIET,
                false, false, 0, 0,
                0, 0, 0, 0, 0, 0, mutableListOf(), 0, 0, 0f)
    }

    fun set(dietPlanEntity: DietPlanEntity) {
        this.dietPlanEntity = dietPlanEntity
    }

    fun get(): DietPlanEntity {
        if (dietPlanEntity.name == INIT_DIET) {
            dietPlanEntity = App.getInstance().db.dietDAO().getAll()[0]
        }
        return dietPlanEntity
    }

    fun getIfExist(): DietPlanEntity {
        return dietPlanEntity
    }

    fun delete() {
        clearDB()
        setEmpty()
    }

    private fun clearDB() {
        Single.fromCallable {
            App.getInstance().db.dietDAO().clearDiet(this.dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }


    fun firstSet(dietPlanEntity: DietPlanEntity, days: List<DietDay>) {
        this.dietPlanEntity = dietPlanEntity
        // set meals states (checked, non checked)
        setMeals(days)
        insertInDB()
        addNewHistoryItem(dietPlanEntity)
    }

    private fun addNewHistoryItem(dietPlanEntity: DietPlanEntity) {
        var history = HistoryDiet()
        history.dietNumber = dietPlanEntity.id
        history.startTime = CustomDate.getClearTime(Calendar.getInstance().timeInMillis)
        history.state = Const.UNCOMPLETED_DIET
        history.difficulty = dietPlanEntity.difficulty
        history.loseLifes = 0
        history.userDifficulty = 0
        history.satisfaction = 4
        history.comment = ""

    }

    fun bindNewDay(days: List<DietDay>): Int {
        checkLastDay()
        var isLastDayHandled = checkNotOpenedDays(days)
        if (dietPlanEntity.currentDay + 1 < days.size) {
            dietPlanEntity.currentDay += 1
            dietPlanEntity.timeTrigger = getTomorrowTimeTrigger()
        }
        if (dietPlanEntity.missingDays > dietPlanEntity.difficulty) {
            return DIET_LOSE
        } else if (isDietNotCompleted(days) && !isLastDayHandled) {
            setMeals(days)
            insertInDB()
            return DIET_CONTINUE
        } else {
            return DIET_COMPLETED
        }
    }

    private fun isDietNotCompleted(days: List<DietDay>): Boolean {
        return days.size >= dietPlanEntity.currentDay + 1
    }

    private fun checkLastDay() {
        if (!isCompletedYesterday() && dietPlanEntity.currentDay !in dietPlanEntity.numbersLosesDays) {
            dietPlanEntity.missingDays += 1
            dietPlanEntity.numbersLosesDays.add(dietPlanEntity.currentDay)
        }
    }

    private fun checkNotOpenedDays(days: List<DietDay>): Boolean {
        var isLastDayHandled = false

        var currentTime = Calendar.getInstance().timeInMillis
        var diff = currentTime - dietPlanEntity.timeTrigger

        var loseDays = (diff / ONE_DAY).toInt()
        var oldCurrentDay = dietPlanEntity.currentDay + 1

        dietPlanEntity.currentDay += loseDays

        if (dietPlanEntity.currentDay > days.size - 1) {
            dietPlanEntity.currentDay = days.size - 1
            isLastDayHandled = true
        }
        //fill loses days
        for (i in oldCurrentDay..dietPlanEntity.currentDay) {
            dietPlanEntity.numbersLosesDays.add(i)
            dietPlanEntity.missingDays += 1
        }
        return isLastDayHandled
    }

    fun rollBack() {
        var diffDaysCount = dietPlanEntity.missingDays - dietPlanEntity.difficulty

        var lastDeletedDay = -1

        for (i in 0 until diffDaysCount) {
            lastDeletedDay = dietPlanEntity.numbersLosesDays[dietPlanEntity.numbersLosesDays.size - 1]
            dietPlanEntity.numbersLosesDays.removeAt(dietPlanEntity.numbersLosesDays.size - 1)
        }

        dietPlanEntity.missingDays = dietPlanEntity.difficulty
        dietPlanEntity.timeTrigger = getTomorrowTimeTrigger()
        dietPlanEntity.currentDay = lastDeletedDay
        setMeals(getDietDays()!!)
        insertInDB()
    }

    private fun insertInDB() {
        Single.fromCallable {
            App.getInstance().db.dietDAO().insert(this.dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }

    fun insertHistoryDietInDB(dietHistory: HistoryDiet) {
        Single.fromCallable {
            App.getInstance().db.dietHistoryDAO().insert(dietHistory)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun setMeals(days: List<DietDay>) {
        dietPlanEntity.breakfastState = NOT_USE
        dietPlanEntity.lunchState = NOT_USE
        dietPlanEntity.dinnerState = NOT_USE
        dietPlanEntity.snakeState = NOT_USE
        dietPlanEntity.snake2State = NOT_USE
        for (eat in days[dietPlanEntity.currentDay].eats) {
            when (eat.type) {
                0 -> setBreakfast(eat.text)
                1 -> setLunch(eat.text)
                2 -> setDinner(eat.text)
                3 -> setSnack(eat.text)
                4 -> setSecondSnack(eat.text)
            }
        }
    }

    private fun setSecondSnack(text: String) {
        if (text != "") {
            dietPlanEntity.snake2State = NOT_CHECKED
        }
    }

    private fun setSnack(text: String) {
        if (text != "") {
            dietPlanEntity.snakeState = NOT_CHECKED
        }
    }

    private fun setDinner(text: String) {
        if (text != "") {
            dietPlanEntity.dinnerState = NOT_CHECKED
        }
    }

    private fun setLunch(text: String) {
        if (text != "") {
            dietPlanEntity.lunchState = NOT_CHECKED
        }
    }

    private fun setBreakfast(text: String) {
        if (text != "") {
            dietPlanEntity.breakfastState = NOT_CHECKED
        }
    }

    fun isCompletedYesterday(): Boolean {
        return dietPlanEntity.breakfastState != NOT_CHECKED
                && dietPlanEntity.lunchState != NOT_CHECKED
                && dietPlanEntity.dinnerState != NOT_CHECKED
                && dietPlanEntity.snakeState != NOT_CHECKED
                && dietPlanEntity.snake2State != NOT_CHECKED
    }


    fun getTomorrowTimeTrigger(): Long {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis + ONE_DAY
    }


    fun checkEat(type: Int) {
        when (type) {
            0 -> dietPlanEntity.breakfastState = CHECKED
            1 -> dietPlanEntity.lunchState = CHECKED
            2 -> dietPlanEntity.dinnerState = CHECKED
            3 -> dietPlanEntity.snakeState = CHECKED
            4 -> dietPlanEntity.snake2State = CHECKED
        }
        insertInDB()
    }

    private fun getDietDays(): List<DietDay>? {
        for (diet in GlobalHolder.getGlobal().allDiets.dietList) {
            if (diet.title == dietPlanEntity.name) {
                return diet.days
            }
        }
        return null
    }

    fun setEmpty() {
        dietPlanEntity = DietPlanEntity(0, 0, NO_DIET_YET,
                false, false, 0, 0,
                0, 0, 0, 0, 0, 0, mutableListOf(), 0, 0, 0f)
    }

    fun setSkipWeight() {
        dietPlanEntity.weightUntil = SKIP_WEIGHT_UNTIL
        insertInDB()
    }

    fun saveWeightUntil(weight: Float) {
        dietPlanEntity.weightUntil = weight
        insertInDB()
    }

    fun createDietHistory(dietState: Int): HistoryDiet {
        var historyDiet = HistoryDiet()
        historyDiet.id = Calendar.getInstance().timeInMillis
        historyDiet.dietNumber = dietPlanEntity.dietNumber
        historyDiet.startTime = dietPlanEntity.startTime
        historyDiet.endTime = CustomDate.getClearTime(Calendar.getInstance().timeInMillis)
        historyDiet.state = dietState
        historyDiet.difficulty = dietPlanEntity.difficulty
        historyDiet.loseLifes = dietPlanEntity.missingDays
        historyDiet.weightUntil = dietPlanEntity.weightUntil
        historyDiet.weightAfter = historyDiet.weightUntil
        historyDiet = HistoryProvider.addAdditionalProperties(historyDiet)
        return historyDiet
    }

}