package com.meal.planner.presentation.profile.backgrounds.pager.pages.dynamic.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.meal.planner.App
import com.meal.planner.R

object InfoLockToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.info_lock_toast, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}