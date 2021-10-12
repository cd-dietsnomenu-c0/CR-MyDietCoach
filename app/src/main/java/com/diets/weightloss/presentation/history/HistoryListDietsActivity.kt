package com.diets.weightloss.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.DietHistory
import com.diets.weightloss.presentation.history.controller.HistoryDietAdapter
import com.diets.weightloss.utils.TimeConverter
import kotlinx.android.synthetic.main.history_list_diets_activity.*
import java.sql.Time

class HistoryListDietsActivity : AppCompatActivity(R.layout.history_list_diets_activity) {

    private var adapter: HistoryDietAdapter? = null
    private var dietList: ArrayList<DietHistory> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fillDietList()
        loadAdditionalProperties()

        rvHistory.layoutManager = LinearLayoutManager(this)
        adapter = HistoryDietAdapter(dietList)
        rvHistory.adapter = adapter

    }

    private fun loadAdditionalProperties() {
        for (i in dietList.indices) {
            var diet = GlobalHolder.getGlobal().allDiets.dietList.find {
                it.index == dietList[i].dietNumber
            }
            var historyDiet = dietList[i]
            historyDiet.imageUrl = diet!!.mainImage
            historyDiet.name = diet!!.title
            historyDiet.readablePeriod = "${TimeConverter.fromMillisToString(historyDiet.startTime)} - ${TimeConverter.fromMillisToString(historyDiet.endTime)}"
            dietList[i] = historyDiet
        }
    }

    private fun fillDietList() {
        dietList.add(DietHistory(0, 3, 1633888830, 1633888830, 0, 2, 2, 3, 4, "kek", "", "", ""))
        dietList.add(DietHistory(0, 6, 1633888830, 1633888830, 1, 1, 2, 3, 4, "kek", "", "", ""))
        dietList.add(DietHistory(0, 70, 1633888830, 1633888830, 0, 0, 2, 3, 4, "kek", "", "", ""))
        dietList.add(DietHistory(0, 45, 1633888830, 1633888830, 1, 2, 2, 3, 4, "kek", "", "", ""))
        dietList.add(DietHistory(0, 31, 1633888830, 1633888830, 0, 1, 2, 3, 4, "kek", "", "", ""))
        dietList.add(DietHistory(0, 13, 1633888830, 1633888830, 1, 0, 2, 3, 4, "kek", "", "", ""))
    }
}