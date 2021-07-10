package com.diets.weightloss.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R

class DaysDialog : DialogFragment() {

    companion object{
        val TAG_FREQUENT_INDEX = "TAG_FREQUENT_INDEX"

        fun newInstance(indexType : Int): DaysDialog {
            val args = Bundle()
            args.putInt(TAG_FREQUENT_INDEX, indexType)
            val fragment = DaysDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.days_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}