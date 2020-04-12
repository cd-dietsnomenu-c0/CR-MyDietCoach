package com.wsoteam.mydietcoach.diets.items.article.interactive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.*
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.DietAdapter
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.IContents
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.managers.LayoutManagerTopScroll
import kotlinx.android.synthetic.main.activity_list_items.*
import kotlinx.android.synthetic.main.diet_act.*
import kotlinx.android.synthetic.main.diet_act.appodealBannerView
import java.util.*

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdWorker.checkLoad()
        appodealBannerView.loadAd(AdRequest.Builder().build())
        diet = intent.getSerializableExtra(Config.NEW_DIET) as Diet
        rvDiet.layoutManager = LayoutManagerTopScroll(this)
        rvDiet.adapter = DietAdapter(diet, object : IContents{
            override fun moveTo(position: Int) {
                //rvDiet.scrollToPosition(position)
                rvDiet.smoothScrollToPosition(position)
            }
        })
    }


    companion object {
         fun falseLoadEmpty() : AllDiets {
            val review = Review("",
                    "",
                    "")


            val eat = Eat(0, "")
            val eat1 = Eat(1, "")
            val eat2 = Eat(2, "")
            val eat3 = Eat(3, "")
            val eat4 = Eat(4, "")

            var eatList = ArrayList<Eat>()
            eatList.add(eat)
            eatList.add(eat1)
            eatList.add(eat4)
            eatList.add(eat2)
            eatList.add(eat3)

            val day = DietDay("", "", eatList)
            var days = ArrayList<DietDay>()
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)

            var benList = ArrayList<String>()
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")

            var consList = ArrayList<String>()
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")

            var diet = Diet("", "", "", "", "", benList, "", consList, "", "", days, "", "", review)

            var listDiets = ArrayList<Diet>()
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)

            var allDiets = AllDiets("", listDiets)

            FirebaseDatabase.getInstance().getReference("empty").setValue(allDiets)

            return allDiets
        }
    }
}