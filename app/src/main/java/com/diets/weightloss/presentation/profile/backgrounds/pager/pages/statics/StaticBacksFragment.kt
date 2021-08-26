package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics.controller.BacksAdapter
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics.controller.IBacks
import kotlinx.android.synthetic.main.static_backs_fragment.*

class StaticBacksFragment : Fragment(R.layout.static_backs_fragment) {

    private lateinit var adapter : BacksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvStaticBacks.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = BacksAdapter(resources.getStringArray(R.array.backgrounds_profile), object : IBacks {
            override fun choiceBack(position: Int) {
                //PreferenceProvider.setBack(position)
                //setBack(position)
                //bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        rvStaticBacks.adapter = adapter
    }
}