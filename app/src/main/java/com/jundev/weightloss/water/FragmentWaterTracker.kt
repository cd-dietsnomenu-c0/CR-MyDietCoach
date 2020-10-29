package com.jundev.weightloss.water

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jundev.weightloss.R
import com.jundev.weightloss.water.controller.DrinkAdapter
import kotlinx.android.synthetic.main.bottom_water_settings.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*

class FragmentWaterTracker : Fragment(R.layout.fragment_water_tracker) {

    var bsWaterSettings: BottomSheetBehavior<LinearLayout>? = null
    var adapter: DrinkAdapter? = null
    var progress = 0.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bsWaterSettings = BottomSheetBehavior.from(llBSWatersettings)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = DrinkAdapter( resources.getStringArray(R.array.water_drinks_names), resources.getIntArray(R.array.water_drinks_factor))
        rvDrinks.adapter = adapter

        btn.setOnClickListener {
            bsWaterSettings!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}