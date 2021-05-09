package com.diets.weightloss.profile.language

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.R
import com.diets.weightloss.profile.language.controllers.ISelectLang
import com.diets.weightloss.profile.language.controllers.LanguagesAdapter
import kotlinx.android.synthetic.main.choice_lang_activity.*

class ChoiceLangActivity : AppCompatActivity(R.layout.choice_lang_activity) {

    private lateinit var adapter : LanguagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = LanguagesAdapter(resources.getStringArray(R.array.langs), 0, object : ISelectLang{
            override fun selectItem(position: Int) {

            }
        })
        rvLanguages.layoutManager = LinearLayoutManager(this)
        rvLanguages.adapter = adapter

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}