package com.diets.weightloss.presentation.history.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.diets.weightloss.App
import com.diets.weightloss.R

object SaveToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.save_toast, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}