package com.wsoteam.mydietcoach.common

import android.icu.util.Calendar
import android.util.Log
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DBHolder {
    var NOT_USE = -1
    var CHECKED = 1
    var NOT_CHECKED = 0
    var ONE_DAY = 86400000

    var DIET_LOSE = -1
    var DIET_CONTINUE = 0
    var DIET_COMPLETED = 1
    private lateinit var dietPlanEntity : DietPlanEntity

    fun set(dietPlanEntity: DietPlanEntity){
        this.dietPlanEntity = dietPlanEntity
    }

    fun get() : DietPlanEntity{
        return dietPlanEntity
    }

    fun firstSet(dietPlanEntity: DietPlanEntity, days: List<DietDay>){
        this.dietPlanEntity = dietPlanEntity
        setMeals(days)
        Single.fromCallable {
            App.getInstance().db.dietDAO().insert(this.dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }

    fun bindNewDay(days : List<DietDay>) : Int{
        dietPlanEntity.currentDay += 1
        dietPlanEntity.timeTrigger = getTomorrowTimeTrigger()
        bindDays()
        checkYesterday()
        if (dietPlanEntity.missingDays > dietPlanEntity.difficulty){
            return DIET_LOSE
        }else if (isDietNotCompleted(days)){
            setMeals(days)
            insertInDB()
            return DIET_CONTINUE
        }else{
            return DIET_COMPLETED
        }
    }

    private fun isDietNotCompleted(days: List<DietDay>): Boolean {
        return days.size >= dietPlanEntity.currentDay + 1
    }

    private fun checkYesterday() {
        if (!isCompletedYesterday()){
            dietPlanEntity.missingDays += 1
        }
    }

    private fun bindDays() {
        var loseDays = ((dietPlanEntity.timeTrigger - Calendar.getInstance().timeInMillis) / ONE_DAY).toInt()
        dietPlanEntity.currentDay += loseDays
        dietPlanEntity.missingDays += loseDays
    }

    private fun insertInDB() {

    }

    private fun setMeals(days: List<DietDay>) {
        dietPlanEntity.breakfastState = NOT_USE
        dietPlanEntity.lunchState = NOT_USE
        dietPlanEntity.dinnerState = NOT_USE
        dietPlanEntity.snakeState = NOT_USE
        dietPlanEntity.dinnerState = NOT_USE
        for (eat in days[dietPlanEntity.currentDay].eats){
            when (eat.type){
                0 -> setBreakfast(eat.text)
                1 -> setLunch(eat.text)
                2 -> setDinner(eat.text)
                3 -> setSnack(eat.text)
                4 -> setSecondSnack(eat.text)
            }
        }
    }

    private fun setSecondSnack(text: String) {
        if (text != ""){
            dietPlanEntity.snake2State = NOT_CHECKED
        }
    }

    private fun setSnack(text: String) {
        if (text != ""){
            dietPlanEntity.snakeState = NOT_CHECKED
        }
    }

    private fun setDinner(text: String) {
        if (text != ""){
            dietPlanEntity.dinnerState = NOT_CHECKED
        }
    }

    private fun setLunch(text: String) {
        if (text != ""){
            dietPlanEntity.lunchState = NOT_CHECKED
        }
    }

    private fun setBreakfast(text: String) {
        if (text != ""){
            dietPlanEntity.breakfastState = NOT_CHECKED
        }
    }

    private fun isCompletedYesterday(): Boolean {
        return  dietPlanEntity.breakfastState != NOT_CHECKED
                && dietPlanEntity.lunchState != NOT_CHECKED
                && dietPlanEntity.dinnerState != NOT_CHECKED
                && dietPlanEntity.snakeState != NOT_CHECKED
                && dietPlanEntity.snake2State != NOT_CHECKED
    }

    fun getCurrentTimeTrigger() : Long{
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getTomorrowTimeTrigger() : Long{
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis + ONE_DAY
    }

    fun checkEat(type: Int) {
        when(type){
            0 -> dietPlanEntity.breakfastState = CHECKED
            1 -> dietPlanEntity.lunchState = CHECKED
            2 -> dietPlanEntity.dinnerState = CHECKED
            3 -> dietPlanEntity.snakeState = CHECKED
            4 -> dietPlanEntity.snake2State = CHECKED
        }
    }
}