package com.wsoteam.mydietcoach.diets.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplitude.api.Amplitude
import com.google.android.gms.ads.AdRequest
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.showAd
import com.wsoteam.mydietcoach.diets.ItemClick
import com.wsoteam.mydietcoach.diets.items.article.ActivityArticle
import com.wsoteam.mydietcoach.diets.items.controllers.ItemAdapter
import kotlinx.android.synthetic.main.activity_list_items.*
import kotlinx.android.synthetic.main.activity_list_items.appodealBannerView
import kotlinx.android.synthetic.main.new_diets_list_activity.*
import java.util.*

class ActivityListItems : AppCompatActivity(R.layout.activity_list_items) {
    private var subsectionArrayList: ArrayList<Subsection>? = null

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdWorker.checkLoad()
        appodealBannerView.loadAd(AdRequest.Builder().build())
        val section = intent.getSerializableExtra(Config.SECTION_DATA) as Section
        subsectionArrayList = section.arrayOfSubSections as ArrayList<Subsection>
        rvSubSections.layoutManager = LinearLayoutManager(this)
        var intent = Intent(this, ActivityArticle::class.java)
        rvSubSections.adapter = ItemAdapter(subsectionArrayList!!, resources.getStringArray(R.array.images), object : ItemClick{
            override fun click(position: Int) {
                intent.putExtra(Config.ITEM_DATA, subsectionArrayList!![position])
                startActivity(intent)
            }

            override fun newDietsClick() {
            }
        })
    }
}