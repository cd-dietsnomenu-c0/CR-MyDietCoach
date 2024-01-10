package com.meal.planner.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import kotlinx.android.synthetic.main.after_norm_dialog.*

class RecentlyIntakeDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.recently_intake_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEnd.setOnClickListener {
            dismiss()
        }
    }
}