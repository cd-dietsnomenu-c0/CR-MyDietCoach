package com.diets.weightloss.presentation.water.statistics.pager.pages.marathons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.model.water.WaterMarathon
import com.diets.weightloss.presentation.water.statistics.pager.pages.marathons.controller.MarathonAdapter
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.android.synthetic.main.marathon_fragment.*
import kotlinx.android.synthetic.main.marathon_fragment.llEmptyState
import kotlinx.android.synthetic.main.segmentation_fragment.*
import java.util.*

class MarathonFragment : Fragment(R.layout.marathon_fragment) {

    lateinit var adapter: MarathonAdapter
    lateinit var vm: MarathonVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProviders.of(this).get(MarathonVM::class.java)

        vm.getMarathons().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                setList(it)
            } else {
                setEmptyState()
            }
        })
    }

    private fun setEmptyState() {
        rvMarathon.visibility = View.INVISIBLE
        llEmptyState.visibility = View.VISIBLE

        if (!PreferenceProvider.isHasPremium){
            /*banner.visibility = View.VISIBLE
            banner.loadAd(AdRequest.Builder().build())*/
        }
    }

    private fun setList(list: List<WaterMarathon>) {
        adapter = MarathonAdapter(list, arrayListOf())
        rvMarathon.layoutManager = LinearLayoutManager(requireContext())
        rvMarathon.adapter = adapter

        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
    }
}