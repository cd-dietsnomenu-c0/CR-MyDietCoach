package com.wsoteam.mydietcoach.diets.items.article.interactive.controller.inside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_eat_breakfast.view.*
import kotlinx.android.synthetic.main.item_eat_dinner.view.*
import kotlinx.android.synthetic.main.item_eat_dinner.view.flBackEatCard
import kotlinx.android.synthetic.main.item_eat_dinner.view.ivTitleImage
import kotlinx.android.synthetic.main.item_eat_dinner.view.tvEatText

class DinnerVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_eat_dinner, viewGroup, false)) {

    fun bind(eat: Eat) {
        Glide.with(itemView.context).load(R.drawable.img_dinner).into(itemView.ivTitleImage)
        itemView.flBackEatCard.setBackgroundResource(R.drawable.gradient_dinner)
        itemView.tvEatText.text = eat.text
    }
}