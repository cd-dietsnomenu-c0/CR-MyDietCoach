package com.meal.planner.presentation.diets.list.modern

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.meal.planner.Config
import com.meal.planner.model.interactive.AllDiets
import com.meal.planner.R
import com.meal.planner.utils.ad.AdWorker
import com.meal.planner.utils.ad.NativeSpeaker
import com.meal.planner.utils.analytics.Ampl
import com.meal.planner.presentation.diets.list.ItemClick
import com.meal.planner.presentation.diets.list.modern.article.DietAct
import com.meal.planner.presentation.diets.list.modern.controllers.InteractiveAdapter
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.new_diets_list_activity.*

class NewDietsListActivity : AppCompatActivity(R.layout.new_diets_list_activity) {

    override fun onBackPressed() {
        AdWorker.showInter(this)
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ampl.openNewDiets()
        AdWorker.checkLoad()
        val allDiets = intent.getSerializableExtra(Config.NEW_DIETS) as AllDiets
        val typeName = intent.getStringExtra(Config.TYPE_NAME)
        val isHasHead = intent.getBooleanExtra(Config.HEADER_TAG, false)
        rvListDiets.layoutManager = LinearLayoutManager(this)
        val adapter = InteractiveAdapter(allDiets, object : ItemClick {
            override fun click(position: Int) {
                startActivity(Intent(this@NewDietsListActivity, DietAct::class.java)
                        .putExtra(Config.NEW_DIET, allDiets.dietList[position])
                        .putExtra(Config.NEED_SHOW_CONNECT, true))
                AdWorker.refreshNativeAd(this@NewDietsListActivity)
            }

            override fun newDietsClick() {
            }
        }, arrayListOf(), typeName!!, isHasHead)
        rvListDiets.adapter = adapter
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