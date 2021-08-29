package com.diets.weightloss.presentation.profile.backgrounds.pager.pages.dynamic.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.backgrounds.pager.ClickBackCallback
import kotlinx.android.synthetic.main.anim_back_vh.view.*

class AnimBackVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val clickBackCallback: ClickBackCallback)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.anim_back_vh, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clickBackCallback.choiceBack(adapterPosition)
    }

    fun bind(url: String) {
        Glide.with(itemView.context).load(url).into(itemView.ivPreview)
    }


}