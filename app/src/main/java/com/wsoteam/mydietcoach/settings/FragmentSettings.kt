package com.wsoteam.mydietcoach.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.openCalculators
import kotlinx.android.synthetic.main.activity_settings.*

class FragmentSettings : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

}