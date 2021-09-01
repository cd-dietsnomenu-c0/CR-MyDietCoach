package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.UnlockCallback
import kotlinx.android.synthetic.main.preview_dialog.*

class PreviewDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.preview_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lavPreview.setAnimation(arguments!!.getString(ANIM_PATH_KEY))
        lavPreview.playAnimation()

        btnClose.setOnClickListener {
            //(targetFragment as UnlockCallback).unlock()
            dismiss()
        }

        btnSetup.setOnClickListener {

        }
    }

    companion object {

        private const val ANIM_PATH_KEY = "ANIM_PATH_KEY"

        fun newInstance(animLoading: String): PreviewDialog {
            var args = Bundle()
            args.putString(ANIM_PATH_KEY, animLoading)
            return PreviewDialog().also {
                it.arguments = args
            }
        }
    }
}