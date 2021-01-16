package com.jundev.weightloss.presentation.profile.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.jundev.weightloss.App
import com.jundev.weightloss.R

object DeniedPermToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_denied, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}