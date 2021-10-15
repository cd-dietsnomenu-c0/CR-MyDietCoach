package com.diets.weightloss.presentation.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.presentation.history.controller.HistoryClickListener
import com.diets.weightloss.presentation.history.controller.HistoryDietAdapter
import com.diets.weightloss.utils.TimeConverter
import kotlinx.android.synthetic.main.history_list_diets_activity.*
import java.sql.Time

class HistoryListDietsActivity : AppCompatActivity(R.layout.history_list_diets_activity) {

    private var adapter: HistoryDietAdapter? = null
    private var listDiet: ArrayList<HistoryDiet> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fillDietList()
        loadAdditionalProperties()

        rvHistory.layoutManager = LinearLayoutManager(this)
        adapter = HistoryDietAdapter(listDiet, object : HistoryClickListener{
            override fun onClick(position: Int) {
                startActivity(HistoryDietActivity.getIntent(this@HistoryListDietsActivity, listDiet[position], true))
            }
        })
        rvHistory.adapter = adapter

    }

    private fun loadAdditionalProperties() {
        for (i in listDiet.indices) {
            var diet = GlobalHolder.getGlobal().allDiets.dietList.find {
                it.index == listDiet[i].dietNumber
            }
            var historyDiet = listDiet[i]
            historyDiet.imageUrl = diet!!.mainImage
            historyDiet.name = diet!!.title
            historyDiet.readableStart = TimeConverter.fromMillisToString(historyDiet.startTime)
            historyDiet.readableEnd = TimeConverter.fromMillisToString(historyDiet.endTime)
            historyDiet.readablePeriod = TimeConverter.getPeriod(historyDiet.startTime, historyDiet.endTime)
            listDiet[i] = historyDiet
        }
    }

    private fun fillDietList() {
        listDiet.add(HistoryDiet(0, 3, 1633888830, 1633888830, 0, 2, 2, 3, 4, "kek", "", "", "", "", 0))
        }
}