package com.wsoteam.mydietcoach.diets.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.xml.validation.Schema

class TypesAdapter(val listSchemas: List<com.wsoteam.mydietcoach.POJOS.schema.Schema>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val BODY_TYPE = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val li = LayoutInflater.from(parent.context)
        return when(viewType){
            BODY_TYPE -> TypesVH(li, parent)
            else -> TypesVH(li, parent)
        }
    }

    override fun getItemCount(): Int {
        return listSchemas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            BODY_TYPE -> (holder as TypesVH).bind(listSchemas[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return BODY_TYPE
    }
}