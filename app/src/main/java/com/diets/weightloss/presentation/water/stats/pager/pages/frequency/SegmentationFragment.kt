package com.diets.weightloss.presentation.water.stats.pager.pages.frequency

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.stats.pager.pages.frequency.controller.FrequencyAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.segmentation_fragment.*
import kotlin.collections.ArrayList


class SegmentationFragment : Fragment(R.layout.segmentation_fragment) {

    lateinit var imgsIndexes : ArrayList<Int>
    lateinit var names : ArrayList<String>
    lateinit var percents : ArrayList<Float>

    lateinit var adapter : FrequencyAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()

        rvFrequency.layoutManager = LinearLayoutManager(requireContext())
        adapter = FrequencyAdapter(imgsIndexes, names, percents, resources.getStringArray(R.array.freq_dividers))
        rvFrequency.adapter = adapter
    }

    private fun fillData() {
        imgsIndexes = arrayListOf()
        for (i in 0..4){
            imgsIndexes.add(resources.obtainTypedArray(R.array.water_drinks_imgs).getResourceId(i, -1))
        }

        names = arrayListOf()
        for (i in 0..4){
            names.add(resources.getStringArray(R.array.water_drinks_names)[i])
        }

        percents = arrayListOf()
        for (i in 0..4){
            percents.add(10f)
        }
    }

}