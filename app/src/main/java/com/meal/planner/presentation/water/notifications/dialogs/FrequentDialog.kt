package com.meal.planner.presentation.water.notifications.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import com.meal.planner.utils.PreferenceProvider
import kotlinx.android.synthetic.main.frequent_dialog.*

class FrequentDialog : DialogFragment() {

    interface Callbacks {
        fun changeFrequent(indexType: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.frequent_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var index = arguments!!.getInt(TAG_FREQUENT_INDEX)
        when (index) {
            PreferenceProvider.TYPE_30 -> rb30min.isChecked = true
            PreferenceProvider.TYPE_60 -> rb60min.isChecked = true
            PreferenceProvider.TYPE_90 -> rb90min.isChecked = true
            PreferenceProvider.TYPE_120 -> rb120min.isChecked = true
            PreferenceProvider.TYPE_150 -> rb150min.isChecked = true
            PreferenceProvider.TYPE_180 -> rb180min.isChecked = true
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnSave.setOnClickListener {
            (requireActivity() as Callbacks).changeFrequent(getFrequentIndex())
            dismiss()
        }
    }

    private fun getFrequentIndex(): Int {
        return when (rgFrequent.checkedRadioButtonId) {
            R.id.rb30min -> PreferenceProvider.TYPE_30
            R.id.rb60min -> PreferenceProvider.TYPE_60
            R.id.rb90min -> PreferenceProvider.TYPE_90
            R.id.rb120min -> PreferenceProvider.TYPE_120
            R.id.rb150min -> PreferenceProvider.TYPE_150
            R.id.rb180min -> PreferenceProvider.TYPE_180
            else -> PreferenceProvider.TYPE_60
        }
    }


    companion object {
        val TAG_FREQUENT_INDEX = "TAG_FREQUENT_INDEX"

        fun newInstance(indexType: Int): FrequentDialog {
            val args = Bundle()
            args.putInt(TAG_FREQUENT_INDEX, indexType)
            val fragment = FrequentDialog()
            fragment.arguments = args
            return fragment
        }
    }
}