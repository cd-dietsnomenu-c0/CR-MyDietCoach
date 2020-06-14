package com.wsoteam.mydietcoach.diets.list.modern.article.toasts

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.R

object AddToast {
    fun showThankToast(context: Context){
        var layout = LayoutInflater.from(context).inflate(R.layout.toast_add_favorite, null)
        var toast = Toast(App.getInstance())
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP or Gravity.RIGHT, 0, 0)
        toast.view = layout
        toast.show()
    }
}