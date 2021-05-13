package com.diets.weightloss.presentation.profile.language

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.SplashActivity
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.presentation.profile.language.controllers.ISelectLang
import com.diets.weightloss.presentation.profile.language.controllers.LanguagesAdapter
import com.diets.weightloss.utils.LangChoicer
import com.diets.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.choice_lang_activity.*

class ChoiceLangActivity : AppCompatActivity(R.layout.choice_lang_activity) {

    private lateinit var adapter: LanguagesAdapter
    private var isChangedLocale = false
    private var oldLocale = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oldLocale = PreferenceProvider.locale
        adapter = LanguagesAdapter(resources.getStringArray(R.array.langs), LangChoicer.getNumber(PreferenceProvider.locale), object : ISelectLang {
            override fun selectItem(position: Int) {
                PreferenceProvider.locale = LangChoicer.getLocaleCode(position)
            }
        })
        rvLanguages.layoutManager = LinearLayoutManager(this)
        rvLanguages.adapter = adapter

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (oldLocale != PreferenceProvider.locale) {
            DBHolder.delete()
            startActivity(SplashActivity.getIntent(true, this))
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }
}