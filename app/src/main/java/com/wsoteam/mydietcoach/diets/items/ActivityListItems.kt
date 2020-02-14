package com.wsoteam.mydietcoach.diets.items

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.Section
import com.wsoteam.mydietcoach.POJOS.Subsection
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.ItemClick
import com.wsoteam.mydietcoach.diets.items.controllers.ItemAdapter
import kotlinx.android.synthetic.main.activity_list_items.*
import java.util.*

class ActivityListItems : AppCompatActivity(R.layout.activity_list_items) {
    private var subsectionArrayList: ArrayList<Subsection>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val section = intent.getSerializableExtra(Config.SECTION_DATA) as Section
        subsectionArrayList = section.arrayOfSubSections as ArrayList<Subsection>
        rvSubSections.layoutManager = LinearLayoutManager(this)
        var intent = Intent(this, ActivityListItems::class.java)
        rvSubSections.adapter = ItemAdapter(subsectionArrayList!!, resources.obtainTypedArray(R.array.images), object : ItemClick{
            override fun click(position: Int) {
                intent.putExtra(Config.ITEM_DATA, subsectionArrayList!![position])
                startActivity(intent)
            }
        })
    }
}