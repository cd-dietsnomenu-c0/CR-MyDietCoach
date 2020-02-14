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
import kotlinx.android.synthetic.main.fr_article.*
import java.util.*

class ActivityArticle : AppCompatActivity(R.layout.fr_article) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var subsection = intent.getSerializableExtra(Config.ITEM_DATA) as Subsection
        Glide.with(this).load(resources.obtainTypedArray(R.array.images).getResourceId(subsection.getUrlOfImage().toInt(), -1)).into(ivCollapsing)
        main_toolbar.title = subsection.getDescription()
        //recyclerView.setLayoutManager(LinearLayoutManager(this))
        //recyclerView.setAdapter(FragmentItem.ItemAdapter(subsection.getArrayOfItemOfSubsection() as ArrayList<ItemOfSubsection?>))
    }
}