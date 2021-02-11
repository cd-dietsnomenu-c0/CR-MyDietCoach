package com.jundev.weightloss.presentation.profile.measurments.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.jundev.weightloss.App
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.toast_refresh.view.*

object RefreshToast {
    fun show(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_refresh, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}