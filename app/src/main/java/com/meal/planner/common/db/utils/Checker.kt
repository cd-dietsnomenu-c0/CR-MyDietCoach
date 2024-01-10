package com.meal.planner.common.db.utils

import com.meal.planner.App
import com.meal.planner.common.DBHolder.setEmpty

object Checker {

    fun checkDB() {
        if (App.getInstance().db.dietDAO().getAll() == null
                || App.getInstance().db.dietDAO().getAll().isEmpty()) {
            setEmpty()
        }
    }
}