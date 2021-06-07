package com.diets.weightloss.presentation.water.stats.pager.pages.frequency

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class SegmentationFragment : Fragment(R.layout.segmentation_fragment) {

    lateinit var imgsIndexes : ArrayList<Int>
    lateinit var names : ArrayList<String>
    lateinit var percents : ArrayList<Float>

    lateinit var adapter : FrequencyAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()

        rvFrequency.layoutManager = LinearLayoutManager(requireContext())
        adapter = FrequencyAdapter(imgsIndexes, names, percents, resources.getStringArray(R.array.freq_dividers), getSpannableString())
        rvFrequency.adapter = adapter
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