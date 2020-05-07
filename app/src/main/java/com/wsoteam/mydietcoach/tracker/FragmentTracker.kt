package com.wsoteam.mydietcoach.tracker

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.DBHolder
import com.wsoteam.mydietcoach.common.GlobalHolder
import com.wsoteam.mydietcoach.tracker.controller.DayAdapter
import com.wsoteam.mydietcoach.tracker.controller.EatAdapter
import kotlinx.android.synthetic.main.fragment_tracker.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvMainCard.setBackgroundResource(R.drawable.shape_card_tracker)
        rvEats.layoutManager = GridLayoutManager(activity, 2)
        rvEats.adapter = EatAdapter(getEats(DBHolder.get().name)!!)

        rvDays.layoutManager = LinearLayoutManager(activity)
        rvDays.adapter = DayAdapter(7)
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
        checkCurrentState()
    }

    private fun checkCurrentState() {
        if (DBHolder.get().timeTrigger < Calendar.getInstance().timeInMillis) {
            DBHolder.refreshTime()
        }
    }

}