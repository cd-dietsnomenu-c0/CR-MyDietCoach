package com.wsoteam.mydietcoach.diets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.POJOS.interactive.AllDiets
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.POJOS.schema.Schema
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.controller.TypesAdapter
import com.wsoteam.mydietcoach.diets.list.modern.NewDietsListActivity
import com.wsoteam.mydietcoach.diets.list.old.OldDietsActivity
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
        adapter = TypesAdapter(global.schemas, object : IClick{
            override fun click(position: Int) {
                openList(position)
            }
        })
        rvTypes.layoutManager = LinearLayoutManager(view.context)
        rvTypes.adapter = adapter
    }

    private fun openList(position: Int) {
        if (global.schemas[position].isOld){
            startActivity(Intent(activity, OldDietsActivity::class.java).putExtra(Config.OLD_DIETS_GLOBAL, global))
        }else{
            startActivity(Intent(activity, NewDietsListActivity::class.java).putExtra(Config.NEW_DIETS, getNewDiets(global.schemas[position])))
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