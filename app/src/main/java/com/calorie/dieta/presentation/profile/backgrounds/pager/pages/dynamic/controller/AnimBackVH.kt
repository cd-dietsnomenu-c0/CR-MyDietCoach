package com.calorie.dieta.presentation.profile.backgrounds.pager.pages.dynamic.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calorie.dieta.R
import com.calorie.dieta.model.back.Background
import com.calorie.dieta.presentation.profile.backgrounds.pager.ClickBackCallback
import kotlinx.android.synthetic.main.anim_back_vh.view.*

class AnimBackVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val clickBackCallback: ClickBackCallback)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.anim_back_vh, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clickBackCallback.choiceBack(adapterPosition)
    }

    fun bind(background: Background) {
        Glide.with(itemView.context).load(background.urlPreview).into(itemView.ivPreview)
        itemView.tvName.text = background.name

        if (background.isUnlock){
            itemView.ivUnlockState.setImageResource(R.drawable.ic_unlock_anim_back)
        }else{
            itemView.ivUnlockState.setImageResource(R.drawable.ic_lock_anim_back)
        }
    }


}