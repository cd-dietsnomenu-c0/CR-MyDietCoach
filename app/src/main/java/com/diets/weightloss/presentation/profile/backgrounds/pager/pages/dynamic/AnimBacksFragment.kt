package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller.AnimBacksAdapter
import kotlinx.android.synthetic.main.anim_backs_fragment.*

class AnimBacksFragment : Fragment(R.layout.anim_backs_fragment) {

    private lateinit var adapter: AnimBacksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AnimBacksAdapter(resources.getStringArray(R.array.back_animations))
        rvAnimBacks.layoutManager = GridLayoutManager(requireContext(), 2)

        rvAnimBacks.adapter = adapter
    }
}