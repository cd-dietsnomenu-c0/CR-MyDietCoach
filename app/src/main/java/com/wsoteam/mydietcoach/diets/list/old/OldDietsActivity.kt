package com.wsoteam.mydietcoach.diets.list.old

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.ad.NativeSpeaker
import com.wsoteam.mydietcoach.analytics.Ampl.Companion.openSubSectionDiets
import com.wsoteam.mydietcoach.diets.list.ItemClick
import com.wsoteam.mydietcoach.diets.list.old.inside.ActivityListItems
import com.wsoteam.mydietcoach.diets.list.old.inside.article.ActivityArticle
import com.wsoteam.mydietcoach.diets.list.old.controllers.SectionAdapter
import kotlinx.android.synthetic.main.old_diets_activity.*
import kotlin.collections.ArrayList

class OldDietsActivity : AppCompatActivity(R.layout.old_diets_activity) {

    lateinit var global: Global
    lateinit var adapter : SectionAdapter
    lateinit var list : ArrayList<Section>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        global = intent.getSerializableExtra(Config.OLD_DIETS_GLOBAL) as Global
        list = global.sectionsArray as ArrayList<Section>
        adapter = SectionAdapter(list, resources.getStringArray(R.array.images), object : ItemClick {
            override fun click(position: Int) {
                var intent = Intent(this@OldDietsActivity, ActivityListItems::class.java)
                if (list[position].arrayOfSubSections.size == 1) {
                    intent = Intent(this@OldDietsActivity, ActivityArticle::class.java)
                    intent.putExtra(Config.ITEM_DATA, list[position].arrayOfSubSections[0])
                } else {
                    openSubSectionDiets(list[position].description)
                    intent.putExtra(Config.SECTION_DATA, list[position])
                }
                startActivity(intent)
            }

            override fun newDietsClick() {

            }
        }, arrayListOf())
        AdWorker.observeOnNativeList(object : NativeSpeaker{
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
        rvDiets.layoutManager = LinearLayoutManager(this)
        rvDiets.adapter = adapter
    }
}