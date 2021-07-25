package com.diets.weightloss.common.db.utils

import com.diets.weightloss.App
import com.diets.weightloss.common.DBHolder.setEmpty

object Checker {

    fun checkDB() {
        if (App.getInstance().db.dietDAO().getAll() == null
                || App.getInstance().db.dietDAO().getAll().isEmpty()) {
            setEmpty()
        }
    }
}