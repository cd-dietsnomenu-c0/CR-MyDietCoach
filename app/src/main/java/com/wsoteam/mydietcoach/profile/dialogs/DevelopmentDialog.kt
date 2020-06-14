package com.wsoteam.mydietcoach.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.tracker.FragmentTracker
import kotlinx.android.synthetic.main.alert_lose.*
import kotlinx.android.synthetic.main.development_dialog.*

class DevelopmentDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.development_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }
}