package com.meal.planner.presentation.water.statistics.pager.pages.frequency.controller

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.meal.planner.R
import com.meal.planner.common.db.entities.water.DrinksCapacities
import com.meal.planner.presentation.water.statistics.pager.pages.frequency.SegmentationFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.pie_chart_vh.view.*

class PieChartVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.pie_chart_vh, viewGroup, false)) {


    private var robotoMedium: Typeface = ResourcesCompat.getFont(itemView.context, R.font.roboto_medium)!!

    init {
        setPieChart()
    }

    fun onBind(drinks: List<DrinksCapacities>, centerText: SpannableString, otherValue: Float) {
        setData(drinks, centerText, otherValue)
    }

    private fun setPieChart() {
        itemView.pcDrinks.description.isEnabled = false
        itemView.pcDrinks.setExtraOffsets(10f, 5f, 90f, 0f)

        itemView.pcDrinks.dragDecelerationFrictionCoef = 0.95f
        itemView.pcDrinks.isDrawHoleEnabled = true
        itemView.pcDrinks.setHoleColor(Color.WHITE)

        itemView.pcDrinks.setCenterTextTypeface(robotoMedium)

        itemView.pcDrinks.setTransparentCircleColor(Color.WHITE)
        itemView.pcDrinks.setTransparentCircleAlpha(110)

        itemView.pcDrinks.holeRadius = 65f
        itemView.pcDrinks.transparentCircleRadius = 69f

        itemView.pcDrinks.setDrawCenterText(true)

        itemView.pcDrinks.rotationAngle = 0f

        itemView.pcDrinks.isRotationEnabled = true
        itemView.pcDrinks.isHighlightPerTapEnabled = true
        itemView.pcDrinks.setUsePercentValues(false)

        itemView.pcDrinks.animateY(1400, Easing.EaseInOutQuad)

        val l: Legend = itemView.pcDrinks.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xOffset = 75f
        l.yOffset = -40f



        itemView.pcDrinks.setDrawEntryLabels(false)
        itemView.pcDrinks.setEntryLabelColor(Color.BLACK)
        //pcDrinks.setEntryLabelTypeface(tfRegular)
        itemView.pcDrinks.setEntryLabelTextSize(12f)
    }

    private fun setData(drinks: List<DrinksCapacities>, centerText: SpannableString, otherValue: Float) {
        itemView.pcDrinks.centerText = centerText

        val entries = ArrayList<PieEntry>()

        for (drink in drinks) {
            entries.add(PieEntry(drink.globalPart,
                    drink.readableName))
        }
        if (otherValue != SegmentationFragment.OTHER_NOT_NEED) {
            entries.add(PieEntry(otherValue,
                    itemView.resources.getString(R.string.other_water)))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(-30f, 0f)
        dataSet.selectionShift = 1f

        val colors = ArrayList<Int>()

        for (c in PIE_CHART_COLORS) colors.add(c)

        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(robotoMedium)
        itemView.pcDrinks.setData(data)

        // undo all highlights
        //chart.highlightValues(null);
        itemView.pcDrinks.invalidate()
    }

    companion object {
        val PIE_CHART_COLORS = intArrayOf(
                Color.rgb(169, 112, 255),
                Color.rgb(255, 137, 92),
                Color.rgb(86, 111, 255),
                Color.rgb(52, 227, 175),
                Color.rgb(76, 195, 255),
                Color.rgb(254, 201, 109),
                Color.rgb(254, 131, 170),
                Color.rgb(104, 56, 104),
                Color.rgb(242, 91, 92)
        )
    }
}