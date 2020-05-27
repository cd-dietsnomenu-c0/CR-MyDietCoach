package com.wsoteam.mydietcoach.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.R

object ThankToast {
    fun showThankToast(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_thank_grade, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}