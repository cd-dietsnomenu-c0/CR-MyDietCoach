package com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.dialogs

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.calorie.dieta.R
import com.calorie.dieta.model.back.Background
import com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.UnlockCallback
import com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.toasts.InfoLockToast
import com.calorie.dieta.utils.PreferenceProvider
import com.calorie.dieta.utils.ad.AdWorker
import com.google.android.gms.ads.FullScreenContentCallback
import kotlinx.android.synthetic.main.item_head.*
import kotlinx.android.synthetic.main.preview_dialog.*

class PreviewDialog : DialogFragment() {

    private var isAdWatched = false
    private var isUnlockNow = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.preview_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var background = arguments!!.getSerializable(BACK_KEY) as Background

        lavPreview.setAnimation(background.path)
        lavPreview.speed = background.speed
        lavPreview.repeatMode = background.mode
        lavPreview.playAnimation()

        if (background.isUnlock) {
            btnSetup.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_set_anim_back, 0, 0, 0)
            btnSetup.text = getString(R.string.setup_back)
        } else {
            btnSetup.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_back_unlock, 0, 0, 0)
            btnSetup.text = getString(R.string.unlock)
        }


        btnClose.setOnClickListener {
            dismiss()
        }

        btnSetup.setOnClickListener {
            if (background.isUnlock || isUnlockNow) {
                (targetFragment as UnlockCallback).setupBackground()
                dismiss()
            } else {
                showRewardVideo()
            }
        }

        if (PreferenceProvider.isHasPremium){
            unlockBack()
        }
    }

    private fun showRewardVideo() {
        if (AdWorker.getRewardAd() != null) {
            AdWorker.getRewardAd()!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    AdWorker.loadReward()
                    if (isAdWatched) {
                        unlockBack()
                    } else {
                        InfoLockToast.show(requireContext())
                    }
                }
            }

            AdWorker.getRewardAd()!!.show(requireActivity()) {
                isAdWatched = true
            }
        } else {
            unlockBack()
        }
    }



    private fun unlockBack() {
        isUnlockNow = true
        btnSetup.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_set_anim_back, 0, 0, 0)
        btnSetup.text = getString(R.string.setup_back)

        flUnlock.visibility = View.VISIBLE

        var alphaAnimator = ValueAnimator.ofFloat(flUnlock.alpha, 0f)
        alphaAnimator.duration = 300
        alphaAnimator.addUpdateListener {
            flUnlock.alpha = it.animatedValue.toString().toFloat()
        }

        lavUnlock.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
                lavPreview.pauseAnimation()
            }

            override fun onAnimationEnd(animation: Animator) {
                lavPreview.resumeAnimation()
                alphaAnimator.start()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        lavUnlock.playAnimation()
        (targetFragment as UnlockCallback).unlockBackground()
    }

    companion object {

        private const val BACK_KEY = "BACK_KEY"

        fun newInstance(background: Background): PreviewDialog {
            var args = Bundle()
            args.putSerializable(BACK_KEY, background)
            return PreviewDialog().also {
                it.arguments = args
            }
        }
    }
}