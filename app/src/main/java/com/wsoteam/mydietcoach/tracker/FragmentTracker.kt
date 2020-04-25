package com.wsoteam.mydietcoach.tracker

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.tracker.controller.DaysAdapter
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.seven_days_layout.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivFirstDay.setOnClickListener {
            lavFirstDay.playAnimation()
        }

        ivSecondDay.setOnClickListener {
            lavSecondDay.playAnimation()
        }

        ivThirdDay.setOnClickListener {
            lavThirdDay.playAnimation()
        }

        ivFourthDay.setOnClickListener {
            lavFourthDay.playAnimation()
        }

        ivFifthDay.setOnClickListener {
            lavFifthDay.playAnimation()
        }

        ivSixthDay.setOnClickListener {
            lavSixthDay.playAnimation()
        }

        ivSeventhDay.setOnClickListener {
            lavSeventhDay.playAnimation()
        }

        ivFourthDay.setBackgroundResource(R.drawable.shape_circle_select)
    }
}