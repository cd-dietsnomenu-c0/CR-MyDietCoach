package com.diets.weightloss.presentation.water.statistics.pager.pages.frequency

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.common.db.entities.water.DrinksCapacities
import com.diets.weightloss.presentation.water.statistics.pager.pages.frequency.controller.FrequencyAdapter
import com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.MarathonVM
import kotlinx.android.synthetic.main.segmentation_fragment.*
import kotlin.collections.ArrayList


class SegmentationFragment : Fragment(R.layout.segmentation_fragment) {

    lateinit var imgsIndexes : ArrayList<Int>
    lateinit var names : ArrayList<String>
    lateinit var percents : ArrayList<Float>

    lateinit var adapter : FrequencyAdapter

    lateinit var vm : SegmentationVH



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(SegmentationVH::class.java)
        vm.getCapacities().observe(this, Observer {
            if (it != null && it.isNotEmpty()){
                fillList(it)
            }else{
                setEmptyState()
            }
        })
        fillData()
    }

    private fun fillList(capacitiesList: List<DrinksCapacities>) {
        rvFrequency.layoutManager = LinearLayoutManager(requireContext())
        adapter = FrequencyAdapter(imgsIndexes, names, percents, resources.getStringArray(R.array.freq_dividers), getSpannableString())
        rvFrequency.adapter = adapter
    }

    private fun setEmptyState() {

    }

    private fun getSpannableString(): SpannableString {
        var span = SpannableString("54 л \n за все время")
        span.setSpan(RelativeSizeSpan(2.0f), 0, span.indexOf("\n"), 0)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.stat_text)), 0, span.indexOf("\n"), 0)
        span.setSpan(StyleSpan(Typeface.BOLD), 0, span.indexOf("\n"), 0)
        span.setSpan(RelativeSizeSpan(0.8f), span.indexOf("\n"), span.length, 0)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.light_gray)), span.indexOf("\n"), span.length, 0)
        return span
    }

    private fun fillData() {
        imgsIndexes = arrayListOf()
        for (i in 0..8){
            imgsIndexes.add(resources.obtainTypedArray(R.array.water_drinks_imgs).getResourceId(i, -1))
        }

        names = arrayListOf()
        for (i in 0..8){
            names.add(resources.getStringArray(R.array.water_drinks_names)[i])
        }

        percents = arrayListOf()
        for (i in 0..8){
            percents.add(15f)
        }
    }

}