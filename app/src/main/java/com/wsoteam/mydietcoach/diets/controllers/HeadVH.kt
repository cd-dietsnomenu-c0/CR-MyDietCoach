package com.wsoteam.mydietcoach.diets.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.head_vh.view.*

class HeadVH(inflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.head_vh, viewGroup, false )) {
    fun bind() {
        Glide.with(itemView.context).load(R.drawable.head_newdiets).into(itemView.ivBackGround)
    }
}