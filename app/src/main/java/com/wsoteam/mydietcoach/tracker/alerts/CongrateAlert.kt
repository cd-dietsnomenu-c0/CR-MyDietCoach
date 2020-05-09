package com.wsoteam.mydietcoach.tracker.alerts

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.mydietcoach.R

class CongrateAlert : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_congrate, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }
}