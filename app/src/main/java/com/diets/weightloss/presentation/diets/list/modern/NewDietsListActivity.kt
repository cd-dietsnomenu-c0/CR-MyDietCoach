package com.diets.weightloss.presentation.diets.list.modern

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.Config
import com.diets.weightloss.model.interactive.AllDiets
import com.diets.weightloss.R
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.presentation.diets.list.ItemClick
import com.diets.weightloss.presentation.diets.list.modern.article.DietAct
import com.diets.weightloss.presentation.diets.list.modern.controllers.InteractiveAdapter
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.android.synthetic.main.new_diets_list_activity.*
import java.util.*

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