package com.diets.weightloss.presentation.water.stats.pager.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.stats.pager.pages.controller.MarathonAdapter
import kotlinx.android.synthetic.main.marathon_fragment.*

class MarathonFragment : Fragment(R.layout.marathon_fragment) {

    lateinit var adapter : MarathonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MarathonAdapter()
        rvMarathon.layoutManager = LinearLayoutManager(requireContext())
        rvMarathon.adapter = adapter
    }
}