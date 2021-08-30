package com.diets.weightloss.presentation.profile.backgrounds.pager

import android.view.ViewGroup
import androidx.core.view.NestedScrollingChild
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BacksVPAdapter(fragmentManager: FragmentManager, val fragmentsList: List<Fragment>) :
        FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }


}