package com.diets.weightloss.presentation.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.presentation.history.controller.HistoryClickListener
import com.diets.weightloss.presentation.history.controller.HistoryDietAdapter
import com.diets.weightloss.utils.history.HistoryProvider
import kotlinx.android.synthetic.main.fr_types.*
import kotlinx.android.synthetic.main.history_list_diets_activity.*
import java.io.Serializable

class HistoryListDietsActivity : AppCompatActivity(R.layout.history_list_diets_activity) {

    private var adapter: HistoryDietAdapter? = null
    private var listDiet: ArrayList<HistoryDiet> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIntent()
        updateUI()
    }

    private fun checkIntent() {
        if (intent.getSerializableExtra(DIET_HISTORY_TAG) != null){
            var historyDiet = intent.getSerializableExtra(DIET_HISTORY_TAG) as HistoryDiet
            startActivity(HistoryDietActivity.getIntent(this@HistoryListDietsActivity, historyDiet, true))
        }
    }


    private fun updateUI() {
        if (listDiet.size == 0) {
            setEmptyUI()
        } else {
            loadAdditionalProperties()

            rvHistory.layoutManager = LinearLayoutManager(this)
            adapter = HistoryDietAdapter(listDiet, object : HistoryClickListener {
                override fun onClick(position: Int) {
                    startActivity(HistoryDietActivity.getIntent(this@HistoryListDietsActivity, listDiet[position], true))
                }
            })
            rvHistory.adapter = adapter

            ivBackToolbar.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setEmptyUI() {
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
    }

    companion object{
        private const val DIET_HISTORY_TAG = "DIET_HISTORY_TAG"

        fun getIntent(historyDiet: HistoryDiet, context: Context) : Intent {
            var intent = Intent(context, HistoryListDietsActivity::class.java)
            intent.putExtra(DIET_HISTORY_TAG, historyDiet)
            return intent
        }
    }


}