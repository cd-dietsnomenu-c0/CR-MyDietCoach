package com.meal.planner.presentation.diets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meal.planner.Config
import com.meal.planner.R
import com.meal.planner.common.GlobalHolder
import com.meal.planner.model.Global
import com.meal.planner.model.interactive.AllDiets
import com.meal.planner.model.interactive.Diet
import com.meal.planner.model.schema.Schema
import com.meal.planner.presentation.diets.controller.TypesAdapter
import com.meal.planner.presentation.diets.dialogs.PropertiesFragment
import com.meal.planner.presentation.diets.list.modern.NewDietsListActivity
import com.meal.planner.presentation.diets.list.old.OldDietsActivity
import com.meal.planner.utils.ad.AdWorker
import com.meal.planner.utils.ad.NativeSpeaker
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fr_types.*

class FragmentTypes : Fragment(R.layout.fr_types) {

    lateinit var adapter : TypesAdapter
    lateinit var global: Global

    companion object{
        var KEY = "FragmentTypes"
        fun newInstance(global: Global) : FragmentTypes{
            var bundle = Bundle()
            bundle.putSerializable(KEY, global)
            var frag = FragmentTypes()
            frag.arguments = bundle
            return frag
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTopMargin()
        global = GlobalHolder.getGlobal()
        adapter = TypesAdapter(global.schemas, arrayListOf(), object : IClick {
            override fun clickOpen(position: Int) {
                openList(position)
            }

            override fun clickProperties(position: Int) {
                PropertiesFragment.newInstance(global.schemas[position].title, global.schemas[position].prop, global.schemas[position].headImage).show(childFragmentManager, "")
            }
        })
        rvTypes.layoutManager = LinearLayoutManager(view.context)
        rvTypes.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                adapter.insertAds(nativeList)
            }
        })


    }

    private fun setTopMargin() {
        var height = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }

        var params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(0, height, 0, 0)
        rvTypes.layoutParams = params
    }

    private fun openList(position: Int) {
        if (global.schemas[position].isOld){
            startActivity(Intent(activity, OldDietsActivity::class.java)
                    .putExtra(Config.OLD_DIETS_GLOBAL, getSections(global)))
        }else{
            startActivity(Intent(activity, NewDietsListActivity::class.java)
                    .putExtra(Config.NEW_DIETS, getNewDiets(global.schemas[position]))
                    .putExtra(Config.TYPE_NAME, global.schemas[position].title)
                    .putExtra(Config.HEADER_TAG, global.schemas[position].plan))
        }
    }

    private fun getSections(global: Global): Global {
        return Global(global.sectionsArray, null, null, null)
    }

    private fun getNewDiets(schema: Schema): AllDiets {
        var reversed = global.allDiets.dietList.reversed()
        var listDiets = mutableListOf<Diet>()
        for (i in schema.reverseDietIndexes.indices){
            listDiets.add(reversed[schema.reverseDietIndexes[i]])
        }
        return AllDiets(schema.title, listDiets.toList())
    }
}