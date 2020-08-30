package com.jundev.weightloss.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jundev.weightloss.POJOS.onboard.OnboardUI
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.ABConfig
import com.jundev.weightloss.utils.PrefWorker
import kotlinx.android.synthetic.main.onboard_activity.*

class OnboardActivity : AppCompatActivity(R.layout.onboard_activity) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var list = if (PrefWorker.getVersion() == ABConfig.A){
            fillA()
        }else{
            fillB()
        }
        vpOnboard.adapter = OnboardAdapter(supportFragmentManager, list)
        diOnboard.setViewPager(vpOnboard)
        vpOnboard.adapter?.registerDataSetObserver(diOnboard.dataSetObserver)
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
}