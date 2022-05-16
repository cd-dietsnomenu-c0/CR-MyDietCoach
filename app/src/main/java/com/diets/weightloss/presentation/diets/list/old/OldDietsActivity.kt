package com.diets.weightloss.presentation.diets.list.old

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.Config
import com.diets.weightloss.model.Global
import com.diets.weightloss.model.Section
import com.diets.weightloss.R
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.utils.analytics.Ampl.Companion.openSubSectionDiets
import com.diets.weightloss.presentation.diets.list.ItemClick
import com.diets.weightloss.presentation.diets.list.old.inside.ActivityListItems
import com.diets.weightloss.presentation.diets.list.old.inside.article.ActivityArticle
import com.diets.weightloss.presentation.diets.list.old.controllers.SectionAdapter
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.android.synthetic.main.old_diets_activity.*
import java.util.*

class OldDietsActivity : AppCompatActivity(R.layout.old_diets_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val global = intent.getSerializableExtra(Config.OLD_DIETS_GLOBAL) as Global
        var list = global.sectionsArray as ArrayList<Section>
        var adapter = SectionAdapter(list, resources.getStringArray(R.array.images), object : ItemClick {
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
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
        rvDiets.layoutManager = LinearLayoutManager(this)
        rvDiets.adapter = adapter

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}