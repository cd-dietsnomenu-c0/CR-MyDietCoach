package com.meal.planner.presentation.diets.list.modern.article.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import com.meal.planner.common.db.entities.EASY_LEVEL
import com.meal.planner.common.db.entities.HARD_LEVEL
import com.meal.planner.common.db.entities.NORMAL_LEVEL
import com.meal.planner.utils.analytics.Ampl
import com.meal.planner.presentation.diets.list.modern.article.DietAct
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
            (activity as DietAct).startDietPlan(EASY_LEVEL)
            dismiss()
        }
        cvMedium.setOnClickListener {
            (activity as DietAct).startDietPlan(NORMAL_LEVEL)
            dismiss()
        }
        cvHard.setOnClickListener {
            (activity as DietAct).startDietPlan(HARD_LEVEL)
            dismiss()
        }
        tvCancel.setOnClickListener {
            dismiss()
        }
    }
}