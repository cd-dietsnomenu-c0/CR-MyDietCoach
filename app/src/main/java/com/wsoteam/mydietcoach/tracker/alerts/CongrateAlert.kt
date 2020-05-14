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
import kotlinx.android.synthetic.main.alert_congrate.*

class CongrateAlert : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_congrate, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabEnd.setOnClickListener {
            (targetFragment as FragmentTracker).closeDiet()
            dismiss()
        }

        fabShare.setOnClickListener {
            //(targetFragment as FragmentTracker).closeDiet()
            dismiss()
        }
    }
}