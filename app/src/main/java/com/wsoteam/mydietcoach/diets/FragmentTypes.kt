package com.wsoteam.mydietcoach.diets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.controller.TypesAdapter
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
        adapter = TypesAdapter(global.schemas)
        rvTypes.layoutManager = LinearLayoutManager(view.context)
        rvTypes.adapter = adapter
    }
}