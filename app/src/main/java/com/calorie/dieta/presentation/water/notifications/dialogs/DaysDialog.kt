package com.calorie.dieta.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.calorie.dieta.R
import com.calorie.dieta.utils.water.workers.DaysWorkers
import kotlinx.android.synthetic.main.days_dialog.*

class DaysDialog : DialogFragment() {


    interface Callbacks{
        fun changeDays(states : List<Boolean>)
    }

    lateinit var listCheckBoxes : List<CheckBox>
    lateinit var listStates : List<Boolean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.days_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listCheckBoxes = arrayListOf(cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday)
        listStates = DaysWorkers.getDaysStates(arguments!!.getString(TAG_DAYS_PATTERN_INDEX)!!)

        for (i in listCheckBoxes.indices){
            listCheckBoxes[i].isChecked = listStates[i]
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            (requireActivity() as Callbacks).changeDays(getStates())
            dismiss()
        }
    }

    private fun getStates(): List<Boolean> {
        var states = arrayListOf<Boolean>()
        for (i in listCheckBoxes.indices){
            states.add(listCheckBoxes[i].isChecked)
        }
        return states
    }


    companion object{
        val TAG_DAYS_PATTERN_INDEX = "TAG_DAYS_PATTERN_INDEX"

        fun newInstance(pattern : String): DaysDialog {
            val args = Bundle()
            args.putString(TAG_DAYS_PATTERN_INDEX, pattern)
            val fragment = DaysDialog()
            fragment.arguments = args
            return fragment
        }
    }
}