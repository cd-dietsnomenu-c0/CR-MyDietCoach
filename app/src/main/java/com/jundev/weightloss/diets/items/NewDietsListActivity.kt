package com.jundev.weightloss.diets.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.jundev.weightloss.Config
import com.jundev.weightloss.POJOS.interactive.AllDiets
import com.jundev.weightloss.R
import com.jundev.weightloss.ad.AdWorker
import com.jundev.weightloss.ad.NativeSpeaker
import com.jundev.weightloss.analytics.Ampl
import com.jundev.weightloss.diets.ItemClick
import com.jundev.weightloss.diets.items.article.interactive.DietAct
import com.jundev.weightloss.diets.items.controllers.interactive.InteractiveAdapter
import kotlinx.android.synthetic.main.new_diets_list_activity.*

class NewDietsListActivity : AppCompatActivity(R.layout.new_diets_list_activity) {

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ampl.openNewDiets()
        AdWorker.checkLoad()
        val allDiets = intent.getSerializableExtra(Config.NEW_DIETS) as AllDiets
        rvListDiets.layoutManager = LinearLayoutManager(this)
        val adapter = InteractiveAdapter(allDiets, object : ItemClick{
            override fun click(position: Int) {
                startActivity(Intent(this@NewDietsListActivity, DietAct::class.java)
                        .putExtra(Config.NEW_DIET, allDiets.dietList[position])
                        .putExtra(Config.NEED_SHOW_CONNECT, true))
                AdWorker.refreshNativeAd(this@NewDietsListActivity)
            }

            override fun newDietsClick() {
            }
        }, arrayListOf())
        rvListDiets.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker{
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
    }
}