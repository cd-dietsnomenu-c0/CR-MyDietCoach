package com.meal.planner.presentation.history.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import kotlinx.android.synthetic.main.weight_after_dialog.*

class WeightAfterDialog: DialogFragment() {

    interface Callbacks {
        fun changeAfterWeight(kilo: Int, gramm: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.weight_after_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        npKilos.minValue = 20
        npKilos.maxValue = 199
        npGramms.minValue = 0
        npGramms.maxValue = 9

        npKilos.value = arguments!!.getInt(KILO_KEY)
        npGramms.value = arguments!!.getInt(GRAMM_KEY)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            (requireActivity() as Callbacks).changeAfterWeight(npKilos.value, npGramms.value)
            dismiss()
        }
    }


    companion object {
        private const val KILO_KEY = "KILO_KEY"
        private const val GRAMM_KEY = "GRAMM_KEY"

        fun newInstance(weight : Pair<Int, Int>): WeightAfterDialog {
            var bundle = Bundle()
            bundle.putInt(KILO_KEY, weight.first)
            bundle.putInt(GRAMM_KEY, weight.second)
            return WeightAfterDialog().also {
                it.arguments = bundle
            }
        }
    }
}