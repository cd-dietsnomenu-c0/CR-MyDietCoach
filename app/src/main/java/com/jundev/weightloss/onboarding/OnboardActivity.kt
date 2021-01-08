package com.jundev.weightloss.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.jundev.weightloss.Config
import com.jundev.weightloss.MainActivity
import com.jundev.weightloss.POJOS.onboard.OnboardUI
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.ABConfig
import com.jundev.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.onboard_activity.*

class OnboardActivity : AppCompatActivity(R.layout.onboard_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var list = if (PreferenceProvider.getVersion() == ABConfig.A){
            fillA()
        }else{
            fillB()
        }
        vpOnboard.adapter = OnboardAdapter(supportFragmentManager, list)
        diOnboard.setViewPager(vpOnboard)
        vpOnboard.adapter?.registerDataSetObserver(diOnboard.dataSetObserver)
        vpOnboard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2 && btnStart.visibility == View.INVISIBLE){
                    btnStart.visibility = View.VISIBLE
                    tvSkip.visibility = View.INVISIBLE
                }else if (position < 2 && btnStart.visibility == View.VISIBLE){
                    btnStart.visibility = View.INVISIBLE
                    tvSkip.visibility = View.VISIBLE
                }
            }
        })

        btnStart.setOnClickListener {
            openMainActivity()
        }
        tvSkip.setOnClickListener {
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).putExtra(Config.PUSH_TAG, intent.getStringExtra(Config.PUSH_TAG)))
    }

    private fun fillA() : List<Fragment> {
        return listOf<Fragment>(AOnboardFragment.newInstance(OnboardUI("Десятки диет", "На любой вкус. Выбирайте подходящие для вас условия и худейте комфортно!", "https://i.ibb.co/gJHGdgj/onboard1.jpg", false)),
                AOnboardFragment.newInstance(OnboardUI("Подробное меню", "Больше не нужно искать в интернете. Описание всех приемов пищи на каждый день диеты!", "https://i.ibb.co/1GdrFmg/onboard2.jpg", false)),
                AOnboardFragment.newInstance(OnboardUI("Трекер диеты", "Веди диету прямо в приложении! Не забывай про приемы пищи благодаря оповещениям", "https://i.ibb.co/WHSnXY4/onboard3.jpg", false)))
    }

    private fun fillB(): List<Fragment> {
        return listOf<Fragment>(BOnboardFragment.newInstance(OnboardUI("Десятки диет", "На любой вкус. Выбирайте подходящие для вас условия и худейте комфортно!", "https://i.ibb.co/pZTNzYR/onboard1.jpg", false)),
                BOnboardFragment.newInstance(OnboardUI("Подробное меню", "Больше не нужно искать в интернете. Описание всех приемов пищи на каждый день диеты!", "https://i.ibb.co/Q85tXgr/onboard3.jpg", false)),
                BOnboardFragment.newInstance(OnboardUI("Трекер диеты", "Веди диету прямо в приложении! Не забывай про приемы пищи благодаря оповещениям", "https://i.ibb.co/JHwSBw2/onboard2.jpg", false)))
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        openMainActivity()
    }
}