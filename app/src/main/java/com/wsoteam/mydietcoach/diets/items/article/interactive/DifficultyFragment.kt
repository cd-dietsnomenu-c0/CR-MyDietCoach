package com.wsoteam.mydietcoach.diets.items.article.interactive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.diff_dialog_fragment.*

class DifficultyFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.diff_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEasy.setOnClickListener {
            (activity as DietAct).startDietPlan(0)
            dismiss()
        }
        btnMedium.setOnClickListener {
            (activity as DietAct).startDietPlan(1)
            dismiss()
        }
        btnHard.setOnClickListener {
            (activity as DietAct).startDietPlan(2)
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}