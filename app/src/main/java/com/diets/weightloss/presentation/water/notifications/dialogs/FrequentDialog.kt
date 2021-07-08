package com.diets.weightloss.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.WaterConfig
import kotlinx.android.synthetic.main.frequent_dialog.*

class FrequentDialog : DialogFragment() {

    companion object{
        val TAG_FREQUENT_INDEX = "TAG_FREQUENT_INDEX"

        fun newInstance(indexType : Int): FrequentDialog {
            val args = Bundle()
            args.putInt(TAG_FREQUENT_INDEX, indexType)
            val fragment = FrequentDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.frequent_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}