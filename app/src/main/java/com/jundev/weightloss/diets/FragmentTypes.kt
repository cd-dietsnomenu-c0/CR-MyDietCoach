package com.jundev.weightloss.diets

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.jundev.weightloss.Config
import com.jundev.weightloss.POJOS.Global
import com.jundev.weightloss.POJOS.interactive.AllDiets
import com.jundev.weightloss.POJOS.interactive.Diet
import com.jundev.weightloss.POJOS.schema.Schema
import com.jundev.weightloss.R
import com.jundev.weightloss.ad.AdWorker
import com.jundev.weightloss.ad.NativeSpeaker
import com.jundev.weightloss.diets.controller.TypesAdapter
import com.jundev.weightloss.diets.list.modern.NewDietsListActivity
import com.jundev.weightloss.diets.list.old.OldDietsActivity
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
        global = arguments!!.getSerializable(KEY) as Global
        adapter = TypesAdapter(global.schemas, arrayListOf(), object : IClick{
            override fun click(position: Int) {
                openList(position)
            }
        })
        rvTypes.layoutManager = LinearLayoutManager(view.context)
        rvTypes.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
    }

    private fun openList(position: Int) {
        if (global.schemas[position].isOld){
            startActivity(Intent(activity, OldDietsActivity::class.java).putExtra(Config.OLD_DIETS_GLOBAL, global))
        }else{
            startActivity(Intent(activity, NewDietsListActivity::class.java)
                    .putExtra(Config.NEW_DIETS, getNewDiets(global.schemas[position]))
                    .putExtra(Config.TYPE_NAME, global.schemas[position].title))
        }
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