package com.meal.planner.presentation.tracker.alerts

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import com.meal.planner.common.db.entities.BREAK_DIET
import com.meal.planner.presentation.tracker.FragmentTracker
import kotlinx.android.synthetic.main.exit_alert.*

class ExitAlert : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.exit_alert, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCancel.setOnClickListener {
            dismiss()
        }

        btnExit.setOnClickListener {
            (targetFragment as FragmentTracker).closeDiet(false, BREAK_DIET)
            dismiss()
        }
    }
}