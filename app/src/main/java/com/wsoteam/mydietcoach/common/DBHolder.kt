package com.wsoteam.mydietcoach.common

import android.util.Log
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DBHolder {
    lateinit var dietPlanEntity : DietPlanEntity

    fun set(dietPlanEntity: DietPlanEntity){
        this.dietPlanEntity = dietPlanEntity
    }

    fun get() : DietPlanEntity{
        return dietPlanEntity
    }

    fun firstSet(dietPlanEntity: DietPlanEntity){
        this.dietPlanEntity = dietPlanEntity
        Single.fromCallable {
            App.getInstance().db.dietDAO().insert(dietPlanEntity)
            null
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> Log.e("LOL", "saved") }) { obj: Throwable -> obj.printStackTrace() }
    }
}