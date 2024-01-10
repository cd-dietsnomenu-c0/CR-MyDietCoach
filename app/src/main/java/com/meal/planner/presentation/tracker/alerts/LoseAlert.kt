package com.meal.planner.presentation.tracker.alerts

import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.meal.planner.R
import com.meal.planner.common.db.entities.LOSE_DIET
import com.meal.planner.presentation.tracker.FragmentTracker
import com.meal.planner.presentation.tracker.toasts.NotShowAdInfoToast
import com.meal.planner.utils.PreferenceProvider
import com.meal.planner.utils.ad.AdWorker
import com.meal.planner.utils.analytics.Ampl
import com.google.android.gms.ads.FullScreenContentCallback
import kotlinx.android.synthetic.main.alert_lose.*

class LoseAlert : DialogFragment() {

    private var isAdWatched = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.alert_lose, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Ampl.loseDiet()

        btnEnd.setOnClickListener {
            Ampl.closeLoseDiet()
            runAnim()
            btnShowAdd.setOnClickListener(null)
            //(targetFragment as FragmentTracker).closeDiet(true, LOSE_DIET)
            //dismiss()
        }

        /*btnReplay.setOnClickListener {
            Ampl.replayLoseDiet()
            (targetFragment as FragmentTracker).restartDiet()
            dismiss()
        }*/


        btnShowAdd.setOnClickListener {
            Ampl.clickRewardDiet()
            showRewardVideo()
        }
    }

    private fun runAnim() {
        var isStartedShow = false

        var showLoad = ValueAnimator.ofFloat(tvLoad.translationX, 0f)
        showLoad.duration = 700L
        showLoad.addUpdateListener {
            tvLoad.translationX = it.animatedValue as Float
            if (it.animatedFraction == 1.0f){
                (targetFragment as FragmentTracker).closeDiet(true, LOSE_DIET)
            }
        }

        var hideBtn = ValueAnimator.ofFloat(btnEnd.translationX, 4000f)
        hideBtn.duration = 1_000L
        hideBtn.addUpdateListener {
            btnEnd.translationX = it.animatedValue as Float
            if (it.animatedFraction >= 0.2f && !isStartedShow){
                isStartedShow = true
                showLoad.start()
            }
        }
        hideBtn.start()

    }


    private fun showRewardVideo() {
        if (AdWorker.getRewardAd() != null && !PreferenceProvider.isHasPremium) {
            Ampl.showRewardDiet()
            AdWorker.getRewardAd()!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    AdWorker.loadReward()
                    if (isAdWatched) {
                        addLife()
                    } else {
                        NotShowAdInfoToast.show(requireContext())
                    }
                }
            }

            AdWorker.getRewardAd()!!.show(requireActivity()) {
                isAdWatched = true
            }
        } else {
            Ampl.notLoadedRewardDiet()
            addLife()
        }
    }

    private fun addLife() {
        (targetFragment as FragmentTracker).rollBack()
        dismiss()
    }
}