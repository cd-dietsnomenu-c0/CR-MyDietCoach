package com.diets.weightloss.presentation.tracker.alerts

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.toasts.InfoLockToast
import com.diets.weightloss.presentation.tracker.FragmentTracker
import com.diets.weightloss.presentation.tracker.toasts.NotShowAdInfoToast
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.analytics.Ampl
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
            (targetFragment as FragmentTracker).closeDiet()
            dismiss()
        }

        btnReplay.setOnClickListener {
            Ampl.replayLoseDiet()
            (targetFragment as FragmentTracker).restartDiet()
            dismiss()
        }


        btnShowAdd.setOnClickListener {
            Ampl.clickRewardDiet()
            showRewardVideo()
        }
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