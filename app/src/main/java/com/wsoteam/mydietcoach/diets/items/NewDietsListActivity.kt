package com.wsoteam.mydietcoach.diets.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.AllDiets
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.diets.ItemClick
import com.wsoteam.mydietcoach.diets.items.article.interactive.DietAct
import com.wsoteam.mydietcoach.diets.items.controllers.interactive.InteractiveAdapter
import kotlinx.android.synthetic.main.new_diets_list_activity.*

class NewDietsListActivity : AppCompatActivity(R.layout.new_diets_list_activity) {

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appodealBannerView.loadAd(AdRequest.Builder().build())
        val allDiets = intent.getSerializableExtra(Config.NEW_DIETS) as AllDiets
        rvListDiets.layoutManager = LinearLayoutManager(this)
        rvListDiets.adapter = InteractiveAdapter(allDiets, object : ItemClick{
            override fun click(position: Int) {
                startActivity(Intent(this@NewDietsListActivity, DietAct::class.java).putExtra(Config.NEW_DIET, allDiets.dietList[position]))
            }

            override fun newDietsClick() {
            }
        })
    }
}