package com.wsoteam.mydietcoach.diets.items.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.Fragments.FragmentItem
import com.wsoteam.mydietcoach.POJOS.ItemOfSubsection
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.items.article.controllers.PartAdapter
import kotlinx.android.synthetic.main.fr_article.*
import java.util.*
import kotlin.collections.ArrayList

class ActivityArticle : AppCompatActivity(R.layout.fr_article) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var subsection = intent.getSerializableExtra(Config.ITEM_DATA) as Subsection
        Glide.with(this).load(resources.obtainTypedArray(R.array.images).getResourceId(subsection.getUrlOfImage().toInt(), -1)).into(ivCollapsing)
        main_toolbar.title = subsection.description
        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = PartAdapter(subsection.arrayOfItemOfSubsection as ArrayList<ItemOfSubsection>)
    }
}