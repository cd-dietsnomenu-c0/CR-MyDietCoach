package com.jundev.weightloss.water.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.item_drink.view.*
import kotlinx.android.synthetic.main.item_eat.view.*

class DrinkVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_drink, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
        itemView.lavCircleComplete.speed = 5f
    }

    fun bind(imageId : Int, name : String, gradientId : Int, hydroFactor : Int){
        Glide.with(itemView.context).load(getImageDrink(imageId)).into(itemView.ivImgDrink)
        itemView.tvDrinkName.text = name
    }


    private fun getImageDrink(imageId: Int): Int {
        return itemView
                .resources
                .obtainTypedArray(R.array.water_drinks_imgs_color)
                .getResourceId(imageId, -1)
    }

    override fun onClick(v: View?) {
        itemView.lavTick.playAnimation()
        itemView.lavCircleComplete.playAnimation()
    }
}