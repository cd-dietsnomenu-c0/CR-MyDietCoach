package com.calorie.dieta.presentation.water.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.calorie.dieta.R
import com.calorie.dieta.presentation.water.WaterConfig
import kotlinx.android.synthetic.main.love_drink_dialog.*

class FrequentDrinkDialog : DialogFragment() {
    
    
    companion object{
        val TAG_NAME_DRINK = "TAG_NAME_DRINK"
        val TAG_CAPACITY_DRINK = "TAG_CAPACITY_DRINK"

        fun newInstance(name : String, capacity : String): FrequentDrinkDialog{
            val args = Bundle()
            args.putString(TAG_NAME_DRINK, name)
            args.putString(TAG_CAPACITY_DRINK, capacity)
            val fragment = FrequentDrinkDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.love_drink_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments!!.getString(TAG_NAME_DRINK) == WaterConfig.EMPTY_FREQUENT_DRINK_NAME){
            tvInfo.text = resources.getString(R.string.frequent_drink_text_empty)
        }else{
            tvInfo.text = resources.getString(R.string.frequent_drink_text, arguments!!.getString(TAG_NAME_DRINK), arguments!!.getString(TAG_CAPACITY_DRINK))
        }


        btnEnd.setOnClickListener {
            dismiss()
        }

    }
}