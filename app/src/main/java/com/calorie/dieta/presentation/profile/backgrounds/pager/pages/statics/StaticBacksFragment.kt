package com.calorie.dieta.presentation.profile.backgrounds.pager.pages.statics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.calorie.dieta.R
import com.calorie.dieta.presentation.profile.ChoiceBackgroundCallback
import com.calorie.dieta.presentation.profile.backgrounds.pager.pages.statics.controller.BacksAdapter
import com.calorie.dieta.presentation.profile.backgrounds.pager.ClickBackCallback
import com.calorie.dieta.utils.PreferenceProvider
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
        }, resources.getStringArray(R.array.static_anim_names))
        rvStaticBacks.adapter = adapter
    }

}