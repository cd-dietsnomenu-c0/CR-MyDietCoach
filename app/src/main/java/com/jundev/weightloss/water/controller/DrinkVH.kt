package com.jundev.weightloss.water.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.item_drink.view.*
import kotlinx.android.synthetic.main.item_eat.view.*

class DrinkVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_drink, viewGroup, false)) {
    fun bind(imageId : Int, name : String, gradientId : Int, hydroFactor : Int){
        //itemView.llDrinkParent.setBackgroundResource(getBackground(gradientId))
        Glide.with(itemView.context).load(getImageDrink(imageId)).into(itemView.ivImgDrink)
        itemView.tvDrinkName.text = name
    }

    private fun getBackground(gradientId: Int): Int {
        return itemView
                .resources
                .obtainTypedArray(R.array.water_drinks_gradients)
                .getResourceId(gradientId, -1)
    }

    private fun getImageDrink(imageId: Int): Int {
        return itemView
                .resources
                .obtainTypedArray(R.array.water_drinks_imgs_color)
                .getResourceId(imageId, -1)
    }
}