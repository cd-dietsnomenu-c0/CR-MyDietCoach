package com.wsoteam.mydietcoach.tracker

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.DBHolder
import com.wsoteam.mydietcoach.common.GlobalHolder
import com.wsoteam.mydietcoach.tracker.controller.DayAdapter
import com.wsoteam.mydietcoach.tracker.controller.eats.EatAdapter
import com.wsoteam.mydietcoach.tracker.controller.eats.IEat
import com.wsoteam.mydietcoach.tracker.controller.lives.LiveAdapter
import com.wsoteam.mydietcoach.tracker.controller.menu.IMenu
import com.wsoteam.mydietcoach.tracker.controller.menu.MenuAdapter
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.vh_native.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {

    val CONGRATE_TAG = "CONGRATE_TAG"
    var currentDay = 0
    var dietState = 0
    var isDayCompleted = false

    lateinit var menuAdapter: MenuAdapter
    lateinit var liveAdapter: LiveAdapter
    lateinit var eatsAdapter: EatAdapter
    lateinit var daysAdapter: DayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvMainCard.setBackgroundResource(R.drawable.shape_card_tracker)
        rvEats.layoutManager = GridLayoutManager(activity, 2)
        rvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvDays.layoutManager = LinearLayoutManager(activity)
        rvLives.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onResume() {
        super.onResume()
        bindTracker()
    }


    private fun bindTracker() {
        if (DBHolder.get().timeTrigger < Calendar.getInstance().timeInMillis) {
            dietState = DBHolder.bindNewDay(getDiet()!!)
        }
        tvTrackerTitle.text = DBHolder.get().name
        currentDay = DBHolder.get().currentDay
        bindLives(DBHolder.get().difficulty, DBHolder.get().missingDays)
        bindEats(getDiet()?.get(currentDay))
        bindMenu()
        bindDays()
        bindDayView()
        if (dietState == DBHolder.DIET_COMPLETED) {
            showCompletedAlert()
        } else if (dietState == DBHolder.DIET_LOSE) {
            showLosedAlert()
        }
    }

    private fun showLosedAlert() {

    }

    private fun showCompletedAlert() {
    }

    private fun bindDays() {
        daysAdapter = DayAdapter(getDiet()!!.size, DBHolder.get().numbersLosesDays, DBHolder.get().currentDay, isDayCompleted)
        rvDays.adapter = daysAdapter
    }


    private fun bindMenu() {
        menuAdapter = MenuAdapter(getTypedArray(), object : IMenu{
            override fun completeDay() {
                closeDay()
            }
        })
        rvMenu.adapter = menuAdapter
    }

    private fun getTypedArray(): List<Int> {
        var list = mutableListOf<Int>()
        if (DBHolder.get().breakfastState == DBHolder.NOT_CHECKED) list.add(0)
        if (DBHolder.get().lunchState == DBHolder.NOT_CHECKED) list.add(1)
        if (DBHolder.get().dinnerState == DBHolder.NOT_CHECKED) list.add(2)
        if (DBHolder.get().snakeState == DBHolder.NOT_CHECKED) list.add(3)
        if (DBHolder.get().snake2State == DBHolder.NOT_CHECKED) list.add(4)
        return list
    }

    private fun bindDayView() {
        tvBigDay.text = (DBHolder.get().currentDay + 1).toString()
        var progress = (DBHolder.get().currentDay + 1).toDouble() / getDiet()!!.size!!.toDouble()
        cpbDay.progress = (progress * 100).toInt()
    }

    private fun bindEats(day: DietDay?) {
        eatsAdapter = EatAdapter(day!!.eats, DBHolder.get().breakfastState,
                DBHolder.get().lunchState, DBHolder.get().dinnerState,
                DBHolder.get().snakeState, DBHolder.get().snake2State, object : IEat {
            override fun checkEat(type: Int) {
                DBHolder.checkEat(type)
                refreshEats(type)
            }
        })
        rvEats.adapter = eatsAdapter
    }

    private fun bindLives(difficulty: Int, missingDays: Int) {
        liveAdapter = LiveAdapter(difficulty + 1, missingDays)
        rvLives.adapter = liveAdapter
    }

    private fun closeDay() {
        isDayCompleted = true
        tvTitleMenu.visibility = View.INVISIBLE
        tvLabelDay.visibility = View.INVISIBLE
        tvBigDay.visibility = View.INVISIBLE

        lavCompleteDay.playAnimation()
        tvCompleteMenu.visibility = View.VISIBLE
        if (this::daysAdapter.isInitialized) {
            daysAdapter.notifyDayCompleted()
        }
    }

    private fun refreshEats(type: Int) {
        menuAdapter.notify(type)
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