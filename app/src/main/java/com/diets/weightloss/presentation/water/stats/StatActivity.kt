package com.diets.weightloss.presentation.water.stats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.stats.pager.StatPagerAdapter
import com.diets.weightloss.presentation.water.stats.pager.pages.marathons.MarathonFragment
import com.diets.weightloss.presentation.water.stats.pager.pages.frequency.SegmentationFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.stat_activity.*

class StatActivity: AppCompatActivity(R.layout.stat_activity) {
    private lateinit var pagerAdapter : StatPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pagerAdapter = StatPagerAdapter(supportFragmentManager, arrayListOf(MarathonFragment(), SegmentationFragment()))
        vpStat.adapter = pagerAdapter
        bindPager()
    }

    private fun bindPager() {
        tlType.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vpStat))

        vpStat.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                tlType.getTabAt(position)!!.select()
            }
        })
    }
}