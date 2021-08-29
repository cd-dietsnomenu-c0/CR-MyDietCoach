package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.ChoiceBackgroundCallback
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller.AnimBacksAdapter
import kotlinx.android.synthetic.main.anim_backs_fragment.*

class AnimBacksFragment : Fragment(R.layout.anim_backs_fragment) {

    private lateinit var adapter: AnimBacksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AnimBacksAdapter(resources.getStringArray(R.array.back_animations), object : ClickBackCallback{
            override fun choiceBack(position: Int) {
                (activity as ChoiceBackgroundCallback).choiceBackground(Const.ANIM_TYPE_BACK, position)
            }
        })
        rvAnimBacks.layoutManager = GridLayoutManager(requireContext(), 2)

        rvAnimBacks.adapter = adapter
    }
}