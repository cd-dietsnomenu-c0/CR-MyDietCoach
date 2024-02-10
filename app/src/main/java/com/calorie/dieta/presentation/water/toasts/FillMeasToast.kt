package com.calorie.dieta.presentation.water.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.calorie.dieta.App
import com.calorie.dieta.R

object FillMeasToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_fill_meas, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}