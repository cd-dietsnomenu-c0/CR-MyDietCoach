package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.ChoiceBackgroundCallback
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller.AnimBacksAdapter
import com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.dialogs.PreviewDialog
import com.diets.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.anim_backs_fragment.*

class AnimBacksFragment : Fragment(R.layout.anim_backs_fragment), UnlockCallback {

    private lateinit var adapter: AnimBacksAdapter
    private var currentIndex = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAnimBacks.setItemViewCacheSize(resources.getStringArray(R.array.preview_animations).size)
        rvAnimBacks.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = AnimBacksAdapter(resources.getStringArray(R.array.preview_animations), object : ClickBackCallback{
            override fun choiceBack(position: Int) {
                currentIndex = position
                var dialog = PreviewDialog.newInstance(getAnimPath(position))
                dialog.setTargetFragment(this@AnimBacksFragment, 0)
                dialog.show(requireFragmentManager(), "")
            }
        })
        rvAnimBacks.adapter = adapter
    }



    private fun getAnimPath(position: Int): String {
        return resources.getStringArray(R.array.back_animations)[position]
    }

    override fun unlock() {
        (parentFragment as ChoiceBackgroundCallback).choiceBackground(PreferenceProvider.ANIM_TYPE_HEAD, currentIndex)
    }
}