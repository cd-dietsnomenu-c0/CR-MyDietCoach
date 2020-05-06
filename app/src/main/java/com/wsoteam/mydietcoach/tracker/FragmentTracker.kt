package com.wsoteam.mydietcoach.tracker

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.DBHolder
import kotlinx.android.synthetic.main.fragment_tracker.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvEats.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onResume() {
        super.onResume()
        checkCurrentState()
    }

    private fun checkCurrentState() {
        if (DBHolder.get().timeTrigger < Calendar.getInstance().timeInMillis){
            DBHolder.refreshTime()
        }
    }

}