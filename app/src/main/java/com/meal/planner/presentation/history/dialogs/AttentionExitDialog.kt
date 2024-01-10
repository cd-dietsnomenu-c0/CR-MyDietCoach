package com.meal.planner.presentation.history.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import kotlinx.android.synthetic.main.attention_exit_dialog.*

class AttentionExitDialog : DialogFragment() {

    interface Callbacks{
        fun saveAndExit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.attention_exit_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnExit.setOnClickListener {
            (requireActivity() as Callbacks).saveAndExit()
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}