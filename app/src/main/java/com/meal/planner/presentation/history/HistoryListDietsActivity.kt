package com.meal.planner.presentation.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.meal.planner.App
import com.meal.planner.R
import com.meal.planner.common.db.entities.HistoryDiet
import com.meal.planner.presentation.history.controller.HistoryClickListener
import com.meal.planner.presentation.history.controller.HistoryDietAdapter
import com.meal.planner.utils.ad.AdWorker
import com.meal.planner.utils.ad.NativeSpeaker
import com.meal.planner.utils.history.HistoryProvider
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fr_types.*
import kotlinx.android.synthetic.main.history_list_diets_activity.*
import kotlinx.android.synthetic.main.load_fragment.*

class HistoryListDietsActivity : AppCompatActivity(R.layout.history_list_diets_activity) {

    private var adapter: HistoryDietAdapter? = null
    private var listDiet: ArrayList<HistoryDiet> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            checkIntent()
        }
    }



    override fun onStart() {
        super.onStart()
        App.getInstance().db.dietHistoryDAO().getAll().observe(this, Observer {
            listDiet = ArrayList(it)
            updateUI()
        })
    }

    override fun onStop() {
        super.onStop()
        App.getInstance().db.dietHistoryDAO().getAll().removeObservers(this)
    }

    private fun checkIntent() {
        if (intent.getSerializableExtra(DIET_HISTORY_TAG) != null) {
            var historyDiet = intent.getSerializableExtra(DIET_HISTORY_TAG) as HistoryDiet
            intent.putExtra(DIET_HISTORY_TAG, HistoryDiet())
            startActivity(HistoryDietActivity.getIntent(this@HistoryListDietsActivity, historyDiet, true))
        }
    }


    private fun updateUI() {
        if (listDiet.size == 0) {
            setEmptyUI()
        } else {
            loadAdditionalProperties()

            lavLoading.pauseAnimation()
            lavLoading.visibility = View.INVISIBLE

            rvHistory.layoutManager = LinearLayoutManager(this)
            adapter = HistoryDietAdapter(listDiet, object : HistoryClickListener {
                override fun onClick(position: Int) {
                    startActivity(HistoryDietActivity.getIntent(this@HistoryListDietsActivity, listDiet[position], false))
                }
            }, arrayListOf())
            rvHistory.adapter = adapter

            AdWorker.observeOnNativeList(object : NativeSpeaker {
                override fun loadFin(nativeList: ArrayList<NativeAd>) {
                    adapter!!.insertAds(nativeList)
                }
            })

            rvHistory.visibility = View.VISIBLE
            ivBackToolbar.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setEmptyUI() {
        lavLoading.pauseAnimation()

        lavLoading.visibility = View.INVISIBLE
        llHistory.visibility = View.INVISIBLE
        llEmpty.visibility = View.VISIBLE
        ivBack.visibility = View.VISIBLE
        lavEmpty.playAnimation()

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadAdditionalProperties() {
        for (i in listDiet.indices) {
            listDiet[i] = HistoryProvider.addAdditionalProperties(listDiet[i])
        }
        listDiet.reverse()
    }

    companion object {
        private const val DIET_HISTORY_TAG = "DIET_HISTORY_TAG"

        fun getIntent(historyDiet: HistoryDiet, context: Context): Intent {
            var intent = Intent(context, HistoryListDietsActivity::class.java)
            intent.putExtra(DIET_HISTORY_TAG, historyDiet)
            return intent
        }
    }


}