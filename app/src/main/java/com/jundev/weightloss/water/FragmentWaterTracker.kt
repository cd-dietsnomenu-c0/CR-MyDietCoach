package com.jundev.weightloss.water

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.PreferenceProvider
import com.jundev.weightloss.water.controller.DrinkAdapter
import com.jundev.weightloss.water.controller.IDrinkAdapter
import com.jundev.weightloss.water.controller.capacities.CapacityAdapter
import com.jundev.weightloss.water.controller.capacities.ICapacityAdapter
import com.jundev.weightloss.water.controller.quick.IQuick
import com.jundev.weightloss.water.controller.quick.QuickAdapter
import com.jundev.weightloss.water.toasts.FillMeasToast
import kotlinx.android.synthetic.main.bottom_begin_meas.*
import kotlinx.android.synthetic.main.bottom_water_settings.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*

class FragmentWaterTracker : Fragment(R.layout.fragment_water_tracker) {

    var bsWaterSettings: BottomSheetBehavior<LinearLayout>? = null
    var bsBeginMeas: BottomSheetBehavior<LinearLayout>? = null
    var drinkTypeAdapter: DrinkAdapter? = null
    var capacityAdapter: CapacityAdapter? = null
    var quickAdapter: QuickAdapter? = null
    var progress = 0.0f
    var listValues: Array<String>? = null

    var sexType = PreferenceProvider.SEX_TYPE_FEMALE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillReadyUI()
        lavTraining.frame = 17

        cvTraining.setOnClickListener {
            lavTraining.playAnimation()
        }

        if (PreferenceProvider.getWeight() == PreferenceProvider.EMPTY) {
            showBeginMeasBS()
        }

        bsWaterSettings!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivDimBackground.visibility = View.GONE
                }
            }
        })
    }


    private fun fillReadyUI() {
        listValues = resources.getStringArray(R.array.drink_capacity_values)
        checkFirstStart()
        fillBS()
        fillQuick()
    }

    private fun showBeginMeasBS() {
        ivDimBackground.visibility = View.VISIBLE
        bsBeginMeas = BottomSheetBehavior.from(llBSBeginMeas)
        bsBeginMeas!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivDimBackground.visibility = View.GONE
                } else if (p1 == BottomSheetBehavior.STATE_DRAGGING) {
                    bsBeginMeas!!.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })
        bsBeginMeas!!.state = BottomSheetBehavior.STATE_EXPANDED

        flFemale.setOnClickListener {
            lavCircleFemale.frame = 0
            lavTickFemale.frame = 0
            lavCircleFemale.visibility = View.VISIBLE
            lavCircleFemale.playAnimation()
            lavTickFemale.visibility = View.VISIBLE
            lavTickFemale.playAnimation()

            lavTickMale.visibility = View.INVISIBLE
            lavCircleMale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_FEMALE
        }

        flMale.setOnClickListener {
            lavCircleMale.frame = 0
            lavTickMale.frame = 0
            lavCircleMale.visibility = View.VISIBLE
            lavCircleMale.playAnimation()
            lavTickMale.visibility = View.VISIBLE
            lavTickMale.playAnimation()

            lavTickFemale.visibility = View.INVISIBLE
            lavCircleFemale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_MALE
        }

        btnContinue.setOnClickListener {
            var weight = 50
            if (edtWeight.text.toString() != "" && edtWeight.text.toString() != " ") {
                weight = edtWeight.text.toString().toInt()
                if (weight in 21..199) {
                    PreferenceProvider.setWeight(weight)
                    PreferenceProvider.setSex(sexType)
                    bsBeginMeas!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    FillMeasToast.show(activity!!)
                    fillTrackerUI()
                } else {
                    Toast.makeText(activity, getString(R.string.input_error_not_in_limit), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, getString(R.string.input_error_empty), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fillTrackerUI() {

    }


    private fun fillQuick() {
        rvQuickDrink.layoutManager = GridLayoutManager(activity, 2)
        quickAdapter = QuickAdapter(object : IQuick {
            override fun onAdd(position: Int) {
                waveHeader.progress = waveHeader.progress + 20
            }

            override fun onSettings(position: Int) {
                ivDimBackground.visibility = View.VISIBLE
                prepareBS(position, PreferenceProvider.getQuickData(position)!!, PreferenceProvider.getCapacityIndex(position)!!)
                bsWaterSettings!!.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
        rvQuickDrink.adapter = quickAdapter
    }

    private fun prepareBS(position: Int, quickData: Int, capacityIndex: Int) {
        drinkTypeAdapter!!.selectNew(quickData)
        capacityAdapter!!.selectNew(capacityIndex)
        rvDrinks.scrollToPosition(quickData)
        rvCapacities.scrollToPosition(capacityIndex)

        btnSaveQuick.setOnClickListener(null)
        btnSaveQuick.setOnClickListener {
            PreferenceProvider.setQuickData(drinkTypeAdapter!!.getSelectedNumber(), position)
            PreferenceProvider.setCapacityIndex(capacityAdapter!!.getSelectedNumber(), position)
            quickAdapter!!.notifyDataSetChanged()
            bsWaterSettings!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun checkFirstStart() {
        if (PreferenceProvider.getQuickData(0) == -1) {
            PreferenceProvider.setQuickData(0, 0)
            PreferenceProvider.setCapacityIndex(1, 0)

            PreferenceProvider.setQuickData(3, 1)
            PreferenceProvider.setCapacityIndex(6, 1)

            PreferenceProvider.setQuickData(4, 2)
            PreferenceProvider.setCapacityIndex(2, 2)

            PreferenceProvider.setQuickData(7, 3)
            PreferenceProvider.setCapacityIndex(3, 3)
        }
    }

    private fun fillBS() {
        bsWaterSettings = BottomSheetBehavior.from(llBSWatersettings)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        drinkTypeAdapter = DrinkAdapter(resources.getStringArray(R.array.water_drinks_names), 1, object : IDrinkAdapter {
            override fun select(newSelect: Int, oldSelect: Int) {
                drinkTypeAdapter!!.unSelect(oldSelect)
            }
        })
        rvDrinks.adapter = drinkTypeAdapter

        rvCapacities.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        capacityAdapter = CapacityAdapter(resources.getStringArray(R.array.drink_capacity_values),
                convertImgsIds(resources.getIntArray(R.array.drink_capacity_icons)), object : ICapacityAdapter {
            override fun select(newSelect: Int, oldSelect: Int) {
                capacityAdapter!!.unSelect(oldSelect)
            }
        }, 1)
        rvCapacities.adapter = capacityAdapter

    }

    private fun convertImgsIds(imgsIds: IntArray): ArrayList<Int> {
        var convertedImgsIds: ArrayList<Int> = arrayListOf()
        for (j in imgsIds.indices) {
            convertedImgsIds.add(resources.obtainTypedArray(R.array.drink_capacity_icons).getResourceId(j, -1))
        }
        return convertedImgsIds
    }
}