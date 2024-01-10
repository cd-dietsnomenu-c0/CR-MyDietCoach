package com.meal.planner.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import kotlinx.android.synthetic.main.end_dialog.*

class EndDialog : DialogFragment() {

    interface Callbacks {
        fun changeEndTime(hour: Int, minute: Int)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.end_dialog, container, false)
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

        npHours.value = arguments!!.getInt(StartDialog.TAG_HOUR)
        npMinutes.value = arguments!!.getInt(StartDialog.TAG_MINUTE)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            (requireActivity() as Callbacks).changeEndTime(npHours.value, npMinutes.value)
            dismiss()
        }
    }


    companion object {
        val TAG_HOUR = "TAG_HOUR"
        val TAG_MINUTE = "TAG_MINUTE"

        fun newInstance(hour: Int, minute: Int): EndDialog {
            val args = Bundle()
            args.putInt(TAG_HOUR, hour)
            args.putInt(TAG_MINUTE, minute)
            val fragment = EndDialog()
            fragment.arguments = args
            return fragment
        }
    }
}