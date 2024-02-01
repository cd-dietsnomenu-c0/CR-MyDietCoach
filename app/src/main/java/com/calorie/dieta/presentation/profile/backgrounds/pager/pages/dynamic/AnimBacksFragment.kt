package com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.calorie.dieta.R
import com.calorie.dieta.presentation.profile.ChoiceBackgroundCallback
import com.calorie.dieta.presentation.profile.backgrounds.pager.ClickBackCallback
import com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.controller.AnimBacksAdapter
import com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.dialogs.PreviewDialog
import com.calorie.dieta.utils.PreferenceProvider
import com.calorie.dieta.utils.backs.AnimBackHolder
import kotlinx.android.synthetic.main.anim_backs_fragment.*

class AnimBacksFragment : Fragment(R.layout.anim_backs_fragment), UnlockCallback {

    private lateinit var adapter: AnimBacksAdapter
    private var currentIndex = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAnimBacks.setItemViewCacheSize(resources.getStringArray(R.array.preview_animations).size)
        rvAnimBacks.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = AnimBacksAdapter(AnimBackHolder.getListBacks(), object : ClickBackCallback{
            override fun choiceBack(position: Int) {
                currentIndex = position
                var dialog = PreviewDialog.newInstance(AnimBackHolder.getListBacks()[position])
                dialog.setTargetFragment(this@AnimBacksFragment, 0)
                dialog.show(requireFragmentManager(), "")
            }
        })
        rvAnimBacks.adapter = adapter
    }


    override fun setupBackground() {
        (parentFragment as ChoiceBackgroundCallback).choiceBackground(PreferenceProvider.ANIM_TYPE_HEAD, currentIndex)
    }

    override fun unlockBackground() {
        AnimBackHolder.unlockItem(currentIndex)
        adapter.unlockItem(currentIndex)
    }
}