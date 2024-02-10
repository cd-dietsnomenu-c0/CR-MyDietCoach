package com.calorie.dieta.presentation.profile.toasts

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.calorie.dieta.App
import com.calorie.dieta.R

object IntroToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_intro, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.TOP or Gravity.RIGHT, 0, 0)
        toast.view = layout
        toast.show()
    }
}