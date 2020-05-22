package com.wsoteam.mydietcoach.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.ad.NativeSpeaker
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.openCalculators
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.cvPrivacy
import kotlinx.android.synthetic.main.activity_settings.cvRate
import kotlinx.android.synthetic.main.activity_settings.cvShare
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.loading_activity.*
import kotlinx.android.synthetic.main.loading_activity.flAdContainer

class FragmentSettings : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                if (nativeList.size > 0) {
                    loadNative(nativeList[0])
                }
            }
        })
        cvPrivacy.setOnClickListener { view -> openPrivacyPage() }
        cvRate.setOnClickListener { view -> rateApp() }
        cvShare.setOnClickListener { view -> shareApp() }
    }

    private fun shareApp() {
        Ampl.openSettingsShare()
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.accompanying_text) + "\n"
                + "https://play.google.com/store/apps/details?id="
                + activity!!.packageName)
        startActivity(intent)
    }

    private fun rateApp() {
        Ampl.openSettingsGrade()
        var intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=" + activity!!.packageName)
        startActivity(intent)
    }

    private fun openPrivacyPage() {
        Ampl.openSettingsPP()
        var intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(activity!!.resources.getString(R.string.url_gdpr))
        startActivity(intent)
    }

    private fun loadNative(nativeAd: UnifiedNativeAd) {
        ad_view.mediaView = ad_media
        ad_view.headlineView = ad_headline
        ad_view.bodyView = ad_body
        ad_view.callToActionView = ad_call_to_action
        ad_view.iconView = ad_icon
        (ad_view.headlineView as TextView).text = nativeAd.headline
        (ad_view.bodyView as TextView).text = nativeAd.body
        (ad_view.callToActionView as Button).text = nativeAd.callToAction
        val icon = nativeAd.icon
        if (icon == null) {
            ad_view.iconView.visibility = View.INVISIBLE
        } else {
            (ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            ad_view.iconView.visibility = View.VISIBLE
        }
        ad_view.setNativeAd(nativeAd)
        flAdContainer.visibility = View.VISIBLE
    }

}