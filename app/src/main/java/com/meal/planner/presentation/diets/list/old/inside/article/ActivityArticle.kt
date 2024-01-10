package com.meal.planner.presentation.diets.list.old.inside.article

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.meal.planner.Config
import com.meal.planner.R
import com.meal.planner.model.ItemOfSubsection
import com.meal.planner.model.Subsection
import com.meal.planner.presentation.diets.list.old.inside.article.controller.PartAdapter
import com.meal.planner.utils.PreferenceProvider.isHasPremium
import com.meal.planner.utils.ad.AdWorker
import com.meal.planner.utils.analytics.Ampl
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fr_article.*

class ActivityArticle : AppCompatActivity(R.layout.fr_article) {

    override fun onBackPressed() {
        AdWorker.showInter(this)
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdWorker.checkLoad()
        if (!isHasPremium) {
            appodealBannerView.visibility = View.VISIBLE
            appodealBannerView.loadAd(AdRequest.Builder().build())
        }
        var subsection = intent.getSerializableExtra(Config.ITEM_DATA) as Subsection
        Glide.with(this).load(resources.getStringArray(R.array.images)[subsection.urlOfImage.toInt()]).into(ivCollapsing)
        main_toolbar.title = subsection.description
        Ampl.openDiet(subsection.description)

        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = PartAdapter(subsection.arrayOfItemOfSubsection as ArrayList<ItemOfSubsection>)

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}