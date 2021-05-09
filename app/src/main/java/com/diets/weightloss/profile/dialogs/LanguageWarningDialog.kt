package com.diets.weightloss.profile.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import com.diets.weightloss.utils.PrefWorker
import kotlinx.android.synthetic.main.language_warning_dialog.*

class LanguageWarningDialog : DialogFragment() {

    interface Callbacks{
        fun openChangeLangActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.language_warning_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PrefWorker.isShowLangWarning = true

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOk.setOnClickListener {
            (targetFragment as Callbacks).openChangeLangActivity()
            dismiss()
        }
    }
}