package com.jundev.weightloss.water

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.PrefWorker
import com.jundev.weightloss.water.controller.DrinkAdapter
import com.jundev.weightloss.water.controller.IDrinkAdapter
import com.jundev.weightloss.water.controller.quick.IQuick
import com.jundev.weightloss.water.controller.quick.QuickAdapter
import kotlinx.android.synthetic.main.bottom_water_settings.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*

class FragmentWaterTracker : Fragment(R.layout.fragment_water_tracker) {

    var bsWaterSettings: BottomSheetBehavior<LinearLayout>? = null
    var adapter: DrinkAdapter? = null
    var quickAdapter: QuickAdapter? = null
    var progress = 0.0f
    var listValues : Array<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listValues = resources.getStringArray(R.array.drink_capacity_values)
        checkFirstStart()
        fillBS()
        fillQuick()
    }

    private fun fillQuick() {
        rvQuickDrink.layoutManager = GridLayoutManager(activity, 2)
        quickAdapter = QuickAdapter(object : IQuick {
            override fun onAdd(position: Int) {
                waveHeader.progress = waveHeader.progress + 20
            }

            override fun onSettings(position: Int) {
                prepareBS(position, PrefWorker.getQuickData(position)!!, PrefWorker.getCapacityIndex(position)!!)
                bsWaterSettings!!.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
        rvQuickDrink.adapter = quickAdapter
    }

    private fun prepareBS(position: Int, quickData: Int, capacityIndex: Int) {
        adapter!!.selectNew(quickData)
        npValues.value = capacityIndex + 1
        changeCapacity(capacityIndex + 1)
        rvDrinks.scrollToPosition(quickData)
    }

    private fun checkFirstStart() {
        if (PrefWorker.getQuickData(0) == -1) {
            PrefWorker.setQuickData(0, 0)
            PrefWorker.setCapacityIndex(1, 0)

            PrefWorker.setQuickData(3, 1)
            PrefWorker.setCapacityIndex(6, 1)

            PrefWorker.setQuickData(4, 2)
            PrefWorker.setCapacityIndex(2, 2)

            PrefWorker.setQuickData(7, 3)
            PrefWorker.setCapacityIndex(3, 3)
        }
    }

    private fun fillBS() {
        bsWaterSettings = BottomSheetBehavior.from(llBSWatersettings)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = DrinkAdapter(resources.getStringArray(R.array.water_drinks_names), 1, object : IDrinkAdapter {
            override fun select(newSelect: Int, oldSelect: Int) {
                adapter!!.unSelect(oldSelect)
            }
        })
        rvDrinks.adapter = adapter

        npValues.minValue = 0
        npValues.maxValue = listValues!!.size
        npValues.displayedValues = listValues
        npValues.minValue = 1

        npValues.setOnScrollListener { view, scrollState ->
            if (scrollState == 0) {
                changeCapacity(view.value)
            }
        }
    }

    private fun changeCapacity(value: Int) {
        tvCurrentCapacity.text = resources.getStringArray(R.array.drink_capacity_names)[value - 1]
        ivCapacity.setImageResource(resources
                .obtainTypedArray(R.array.drink_capacity_icons)
                .getResourceId(value - 1, -1))
    }
}