package com.diets.weightloss.presentation.tracker.alerts

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.block_win_dialog.*

class BlockWinDialog : DialogFragment() {

    interface Callback{
        fun endAnim()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.block_win_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lavCheck.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                tvLoad.visibility = View.VISIBLE
                (targetFragment as Callback).endAnim()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }
}