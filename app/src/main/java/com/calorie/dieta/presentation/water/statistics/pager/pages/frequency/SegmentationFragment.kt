package com.calorie.dieta.presentation.water.statistics.pager.pages.frequency

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
import com.calorie.dieta.R
import com.calorie.dieta.common.db.entities.water.DrinksCapacities
import com.calorie.dieta.presentation.water.statistics.pager.pages.frequency.controller.FrequencyAdapter
import com.calorie.dieta.utils.PreferenceProvider
import com.calorie.dieta.utils.ad.AdWorker
import com.calorie.dieta.utils.ad.NativeSpeaker
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.meas_activitys.*
import kotlinx.android.synthetic.main.segmentation_fragment.*
import java.text.DecimalFormat


class SegmentationFragment : Fragment(R.layout.segmentation_fragment) {

    lateinit var imgsIndexes: ArrayList<Int>
    lateinit var names: ArrayList<String>
    lateinit var percents: ArrayList<Float>

    lateinit var adapter: FrequencyAdapter

    lateinit var vm: SegmentationVM

    private val COUNT_DRINKS_FOR_PIE_CHART = 8

    companion object{
        const val OTHER_NOT_NEED = -1f
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(SegmentationVM::class.java)
        vm.getCapacities().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                fillList(it)
            } else {
                setEmptyState()
            }
        })
        fillData()

    }

    private fun fillList(drinks: List<DrinksCapacities>) {
        rvFrequency.layoutManager = LinearLayoutManager(requireContext())
        adapter = FrequencyAdapter(drinks, getSpannableString(drinks), getShowedDrinks(drinks), getOtherPercent(drinks), arrayListOf())
        rvFrequency.adapter = adapter

        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
    }

    private fun getOtherPercent(drinks: List<DrinksCapacities>): Float {
        if (drinks.size > COUNT_DRINKS_FOR_PIE_CHART ) {
            var percent = 0f
            for (i in 0 until COUNT_DRINKS_FOR_PIE_CHART) {
                percent += drinks[i].globalPart
            }

            return 100.0f - percent
        }else{
            return OTHER_NOT_NEED
        }
    }

    private fun getShowedDrinks(drinks: List<DrinksCapacities>): List<DrinksCapacities> {
        return if (drinks.size > COUNT_DRINKS_FOR_PIE_CHART){
            drinks.subList(0, COUNT_DRINKS_FOR_PIE_CHART - 1)
        }else{
            drinks
        }

    }

    private fun setEmptyState() {
        llEmptyState.visibility = View.VISIBLE
        rvFrequency.visibility = View.GONE

        if (!PreferenceProvider.isHasPremium){
            banner.visibility = View.VISIBLE
            banner.loadAd(AdRequest.Builder().build())
        }
    }



    private fun getSpannableString(drinks: List<DrinksCapacities>): SpannableString {
        var globalCapacity = 0L
        for (drink in drinks) {
            globalCapacity += drink.dirtyCapacity
        }

        var formater = DecimalFormat("#0.0")
        var value: Float = globalCapacity.toFloat() / 1000f

        var text = formater.format(value)

        var span = SpannableString(getString(R.string.global_capacity, text))
        span.setSpan(RelativeSizeSpan(2.0f), 0, span.indexOf("\n"), 0)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.stat_text)), 0, span.indexOf("\n"), 0)
        span.setSpan(StyleSpan(Typeface.BOLD), 0, span.indexOf("\n"), 0)
        span.setSpan(RelativeSizeSpan(0.8f), span.indexOf("\n"), span.length, 0)
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.light_gray)), span.indexOf("\n"), span.length, 0)
        return span
    }

    private fun fillData() {
        imgsIndexes = arrayListOf()
        for (i in 0..8) {
            imgsIndexes.add(resources.obtainTypedArray(R.array.water_drinks_imgs).getResourceId(i, -1))
        }

        names = arrayListOf()
        for (i in 0..8) {
            names.add(resources.getStringArray(R.array.water_drinks_names)[i])
        }

        percents = arrayListOf()
        for (i in 0..8) {
            percents.add(15f)
        }
    }

}