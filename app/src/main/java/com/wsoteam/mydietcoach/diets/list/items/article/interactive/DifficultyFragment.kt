package com.wsoteam.mydietcoach.diets.list.items.article.interactive

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.analytics.Ampl
import kotlinx.android.synthetic.main.diff_dialog_fragment.*

class DifficultyFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.diff_dialog_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        Ampl.showHardCard()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cvEasy.setOnClickListener {
            (activity as DietAct).startDietPlan(2)
            dismiss()
        }
        cvMedium.setOnClickListener {
            (activity as DietAct).startDietPlan(1)
            dismiss()
        }
        cvHard.setOnClickListener {
            (activity as DietAct).startDietPlan(0)
            dismiss()
        }
        tvCancel.setOnClickListener {
            dismiss()
        }
    }
}