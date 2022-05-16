package com.diets.weightloss.presentation.diets.list.old.inside

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.Config
import com.diets.weightloss.model.Section
import com.diets.weightloss.model.Subsection
import com.diets.weightloss.R
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.presentation.diets.list.ItemClick
import com.diets.weightloss.presentation.diets.list.old.inside.article.ActivityArticle
import com.diets.weightloss.presentation.diets.list.old.inside.controller.ItemAdapter
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.android.synthetic.main.activity_list_items.*
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
        val section = intent.getSerializableExtra(Config.SECTION_DATA) as Section
        subsectionArrayList = section.arrayOfSubSections as ArrayList<Subsection>
        rvSubSections.layoutManager = LinearLayoutManager(this)
        var intent = Intent(this, ActivityArticle::class.java)
        var adapter = ItemAdapter(subsectionArrayList!!, resources.getStringArray(R.array.images), object : ItemClick {
            override fun click(position: Int) {
                intent.putExtra(Config.ITEM_DATA, subsectionArrayList!![position])
                startActivity(intent)
                AdWorker.refreshNativeAd(this@ActivityListItems)
            }

            override fun newDietsClick() {
            }
        }, arrayListOf())
        rvSubSections.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker{
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}