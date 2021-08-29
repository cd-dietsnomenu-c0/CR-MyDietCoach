package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.ChoiceBackgroundCallback
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics.controller.BacksAdapter
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback
import com.diets.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.static_backs_fragment.*

class StaticBacksFragment : Fragment(R.layout.static_backs_fragment) {

    private lateinit var adapter : BacksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvStaticBacks.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = BacksAdapter(resources.getStringArray(R.array.backgrounds_profile), object : ClickBackCallback {
            override fun choiceBack(position: Int) {
                (parentFragment as ChoiceBackgroundCallback).choiceBackground(PreferenceProvider.STATIC_TYPE_HEAD, position)
            }
        })
        rvStaticBacks.adapter = adapter

        rvStaticBacks.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            v.onTouchEvent(event)
            true
        }
    }
}