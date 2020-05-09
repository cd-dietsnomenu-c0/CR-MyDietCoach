package com.wsoteam.mydietcoach.tracker

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.DBHolder
import com.wsoteam.mydietcoach.common.GlobalHolder
import com.wsoteam.mydietcoach.tracker.controller.DayAdapter
import com.wsoteam.mydietcoach.tracker.controller.eats.EatAdapter
import com.wsoteam.mydietcoach.tracker.controller.eats.IEat
import com.wsoteam.mydietcoach.tracker.controller.menu.MenuAdapter
import kotlinx.android.synthetic.main.fragment_tracker.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {

    val CONGRATE_TAG = "CONGRATE_TAG"
    val DIET_LOSE = true
    var currentDay = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvMainCard.setBackgroundResource(R.drawable.shape_card_tracker)
        rvEats.layoutManager = GridLayoutManager(activity, 2)
        rvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        rvDays.layoutManager = LinearLayoutManager(activity)
        rvDays.adapter = DayAdapter(7)
        for (eat in GlobalHolder.getGlobal().allDiets.dietList[0].days[0].eats){
            Log.e("LOL", eat.type.toString())
        }
    }

    private fun getEats(name: String): List<Eat>? {
        for (diet in GlobalHolder.getGlobal().allDiets.dietList) {
            if (diet.title == name) {
                return diet.days[0].eats
            }
        }
        return null
    }

    override fun onResume() {
        super.onResume()
        bindDB()
        bindMenu()
    }

    private fun bindMenu() {
        rvMenu.adapter = MenuAdapter(listOf(0, 1, 2, 3, 4))
    }

    private fun bindDB() {
        if (DBHolder.get().timeTrigger < Calendar.getInstance().timeInMillis) {
            DBHolder.bindNewDay(getDiet()!!)
        }
        currentDay = DBHolder.get().currentDay
        bindLives(DBHolder.get().difficulty, DBHolder.get().missingDays)
        bindEats(getDiet()?.get(currentDay))
    }

    private fun bindEats(day: DietDay?) {
        rvEats.adapter = EatAdapter(day!!.eats, DBHolder.get().breakfastState,
                DBHolder.get().lunchState, DBHolder.get().breakfastState,
                DBHolder.get().snakeState, DBHolder.get().snake2State, object : IEat{
            override fun checkEat(type: Int) {
                DBHolder.checkEat(type)
            }
        })
    }

    private fun bindLives(difficulty: Int, missingDays: Int) {

    }

    private fun getDiet(): List<DietDay>? {
        for (diet in GlobalHolder.getGlobal().allDiets.dietList) {
            if (diet.title == DBHolder.get().name) {
                return diet.days
            }
        }
        return null
    }

}