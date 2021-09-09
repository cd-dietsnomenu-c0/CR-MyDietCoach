package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.statics.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback
import kotlinx.android.synthetic.main.item_back.view.*

class BacksVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iBacks: ClickBackCallback) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_back, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }
    fun bind(imageUrl: String, name: String) {
        Glide.with(itemView.context).load(imageUrl).into(itemView.ivBack)
        itemView.tvName.text = name
    }

    override fun onClick(v: View?) {
        iBacks.choiceBack(adapterPosition)
    }
}