package com.meal.planner.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import kotlinx.android.synthetic.main.start_dialog.*

class StartDialog : DialogFragment() {

    interface Callbacks {
        fun changeStartTime(hour: Int, minute: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.start_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        npHours.minValue = 0
        npHours.maxValue = 23
        npMinutes.minValue = 0
        npMinutes.maxValue = 59

        npHours.setFormatter { value -> String.format("%02d", value) }
        npMinutes.setFormatter { value -> String.format("%02d", value) }

        npHours.value = arguments!!.getInt(TAG_HOUR)
        npMinutes.value = arguments!!.getInt(TAG_MINUTE)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            (requireActivity() as Callbacks).changeStartTime(npHours.value, npMinutes.value)
            dismiss()
        }
    }


    companion object {
        val TAG_HOUR = "TAG_HOUR"
        val TAG_MINUTE = "TAG_MINUTE"

        fun newInstance(hour: Int, minute: Int): StartDialog {
            val args = Bundle()
            args.putInt(TAG_HOUR, hour)
            args.putInt(TAG_MINUTE, minute)
            val fragment = StartDialog()
            fragment.arguments = args
            return fragment
        }
    }
}