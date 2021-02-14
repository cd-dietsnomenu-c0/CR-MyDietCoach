package com.diets.weightloss.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.diets.weightloss.App
import com.diets.weightloss.R

object ThankToast {
    fun showThankToast(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_thank_grade, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}