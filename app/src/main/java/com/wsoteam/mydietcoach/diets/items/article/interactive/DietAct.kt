package com.wsoteam.mydietcoach.diets.items.article.interactive

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.*
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.DietAdapter
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.IContents
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.managers.LayoutManagerTopScroll
import kotlinx.android.synthetic.main.activity_list_items.*
import kotlinx.android.synthetic.main.diet_act.*
import kotlinx.android.synthetic.main.diet_act.appodealBannerView
import java.util.*

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet
    private var TAG = "ALERT_DIET_ACT"

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdWorker.checkLoad()
        appodealBannerView.loadAd(AdRequest.Builder().build())
        diet = intent.getSerializableExtra(Config.NEW_DIET) as Diet
        rvDiet.layoutManager = LayoutManagerTopScroll(this)
        rvDiet.adapter = DietAdapter(diet, object : IContents {
            override fun moveTo(position: Int) {
                rvDiet.smoothScrollToPosition(position)
            }
        })
        Ampl.openNewDiet(diet.title)

        btnStart.setOnClickListener {
            DifficultyFragment().show(supportFragmentManager, TAG)
        }
    }

    fun startDietPlan(difficulty: Int) {
        Log.e("LOL", difficulty.toString())
    }

}