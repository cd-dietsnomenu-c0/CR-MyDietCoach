package com.wsoteam.mydietcoach.diets.items.article.interactive.controller.inside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.item_eat_breakfast.view.*
import kotlinx.android.synthetic.main.item_eat_lunch.view.*
import kotlinx.android.synthetic.main.item_eat_lunch.view.flBackEatCard
import kotlinx.android.synthetic.main.item_eat_lunch.view.ivTitleImage
import kotlinx.android.synthetic.main.item_eat_lunch.view.tvEatText

class LunchVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_eat_lunch, viewGroup, false)) {
    fun bind(eat: Eat) {
        Glide.with(itemView.context).load(R.drawable.img_lunch).into(itemView.ivTitleImage)
        itemView.flBackEatCard.setBackgroundResource(R.drawable.gradient_lunch)
        itemView.tvEatText.text = eat.text
    }

}