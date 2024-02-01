package com.calorie.dieta.common.db.utils

import com.calorie.dieta.App
import com.calorie.dieta.common.DBHolder.setEmpty

object Checker {

    fun checkDB() {
        if (App.getInstance().db.dietDAO().getAll() == null
                || App.getInstance().db.dietDAO().getAll().isEmpty()) {
            setEmpty()
        }
    }
}