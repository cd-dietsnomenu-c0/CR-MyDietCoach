package com.meal.planner.presentation.profile.measurments.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.meal.planner.App
import com.meal.planner.R

object RefreshToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_refresh, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}