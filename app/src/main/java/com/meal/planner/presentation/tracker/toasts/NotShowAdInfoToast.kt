package com.meal.planner.presentation.tracker.toasts

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.meal.planner.App
import com.meal.planner.R

object NotShowAdInfoToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.not_show_ad_info_toast, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.TOP or Gravity.RIGHT, 0, 0)
        toast.view = layout
        toast.show()
    }
}