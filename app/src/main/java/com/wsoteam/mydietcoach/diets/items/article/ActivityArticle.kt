package com.wsoteam.mydietcoach.diets.items.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplitude.api.Amplitude
import com.appodeal.ads.Appodeal
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.ItemOfSubsection
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.showAd
import com.wsoteam.mydietcoach.diets.items.article.controllers.PartAdapter
import kotlinx.android.synthetic.main.fr_article.*

class ActivityArticle : AppCompatActivity(R.layout.fr_article) {

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

        var subsection = intent.getSerializableExtra(Config.ITEM_DATA) as Subsection
        Glide.with(this).load(resources.getStringArray(R.array.images)[subsection.urlOfImage.toInt()]).into(ivCollapsing)
        main_toolbar.title = subsection.description
        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = PartAdapter(subsection.arrayOfItemOfSubsection as ArrayList<ItemOfSubsection>)
    }
}