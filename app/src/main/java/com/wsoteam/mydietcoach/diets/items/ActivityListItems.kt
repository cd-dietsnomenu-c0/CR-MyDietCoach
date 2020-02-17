package com.wsoteam.mydietcoach.diets.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplitude.api.Amplitude
import com.appodeal.ads.Appodeal
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.showAd
import com.wsoteam.mydietcoach.diets.ItemClick
import com.wsoteam.mydietcoach.diets.items.article.ActivityArticle
import com.wsoteam.mydietcoach.diets.items.controllers.ItemAdapter
import kotlinx.android.synthetic.main.activity_list_items.*
import java.util.*

class ActivityListItems : AppCompatActivity(R.layout.activity_list_items) {
    private var subsectionArrayList: ArrayList<Subsection>? = null

    override fun onBackPressed() {
        checkPermissionForShowInter()
        super.onBackPressed()
    }

    private fun checkPermissionForShowInter() {
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
            showAd()
            Amplitude.getInstance().logEvent("show ad")
            Appodeal.show(this, Appodeal.INTERSTITIAL)
            Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                    Appodeal.INTERSTITIAL, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Appodeal.setBannerViewId(R.id.appodealBannerView)
        Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                Appodeal.INTERSTITIAL or Appodeal.BANNER, true)
        Appodeal.show(this, Appodeal.BANNER_VIEW)

        val section = intent.getSerializableExtra(Config.SECTION_DATA) as Section
        subsectionArrayList = section.arrayOfSubSections as ArrayList<Subsection>
        rvSubSections.layoutManager = LinearLayoutManager(this)
        var intent = Intent(this, ActivityArticle::class.java)
        rvSubSections.adapter = ItemAdapter(subsectionArrayList!!, resources.getStringArray(R.array.images), object : ItemClick{
            override fun click(position: Int) {
                intent.putExtra(Config.ITEM_DATA, subsectionArrayList!![position])
                startActivity(intent)
            }
        })
    }
}