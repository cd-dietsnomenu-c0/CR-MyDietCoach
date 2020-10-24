package com.jundev.weightloss.water

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jundev.weightloss.R
import com.jundev.weightloss.water.controller.DrinkAdapter
import kotlinx.android.synthetic.main.fragment_water_tracker.*

class FragmentWaterTracker : Fragment(R.layout.fragment_water_tracker) {
    var adapter: DrinkAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = DrinkAdapter( resources.getStringArray(R.array.water_drinks_names), resources.getIntArray(R.array.water_drinks_factor))
        rvDrinks.adapter = adapter

    }
}