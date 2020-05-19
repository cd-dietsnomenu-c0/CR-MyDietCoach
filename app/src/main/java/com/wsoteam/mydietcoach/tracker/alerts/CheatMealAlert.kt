package com.wsoteam.mydietcoach.tracker.alerts

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.tracker.FragmentTracker
import kotlinx.android.synthetic.main.alert_cheat_meal.*
import kotlinx.android.synthetic.main.alert_congrate.*

class CheatMealAlert : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_cheat_meal, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEnd.setOnClickListener {
            dismiss()
        }
    }
}