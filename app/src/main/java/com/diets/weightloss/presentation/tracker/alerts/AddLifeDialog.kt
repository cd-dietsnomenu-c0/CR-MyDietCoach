package com.diets.weightloss.presentation.tracker.alerts

import android.graphics.drawable.ColorDrawable
import android.icu.util.TimeUnit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.add_life_dialog.*
import kotlinx.coroutines.GlobalScope

class AddLifeDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.add_life_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnEnd.setOnClickListener {
            dismiss()
        }
    }
}
