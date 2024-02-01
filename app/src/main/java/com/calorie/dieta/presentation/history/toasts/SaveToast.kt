package com.calorie.dieta.presentation.history.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.calorie.dieta.App
import com.calorie.dieta.R

object SaveToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.save_toast, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}