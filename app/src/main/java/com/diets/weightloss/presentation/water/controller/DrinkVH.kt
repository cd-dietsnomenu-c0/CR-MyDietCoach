package com.diets.weightloss.presentation.water.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.item_drink.view.*

class DrinkVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iDrinkVH: IDrinkAdapter) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_drink, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
        itemView.lavCircleComplete.speed = 5f
    }

    fun bind(imageId : Int, name : String, isNeedSelect : Boolean){
        Glide.with(itemView.context).load(getImageDrink(imageId)).into(itemView.ivImgDrink)
        itemView.tvDrinkName.text = name
        if (isNeedSelect){
            itemView.lavCircleComplete.progress = 1f
            itemView.lavTick.progress = 1f
            itemView.lavCircleComplete.visibility = View.VISIBLE
            itemView.lavTick.visibility = View.VISIBLE
        }else{
            itemView.lavCircleComplete.visibility = View.INVISIBLE
            itemView.lavTick.visibility = View.INVISIBLE
            itemView.lavCircleComplete.progress = 0f
            itemView.lavTick.progress = 0f
        }
    }


    private fun getImageDrink(imageId: Int): Int {
        return itemView
                .resources
                .obtainTypedArray(R.array.water_drinks_imgs_color)
                .getResourceId(imageId, -1)
    }

    override fun onClick(v: View?) {
        itemView.lavCircleComplete.visibility = View.VISIBLE
        itemView.lavTick.visibility = View.VISIBLE
        itemView.lavCircleComplete.playAnimation()
        itemView.lavTick.playAnimation()
        iDrinkVH.select(adapterPosition, -1)
    }
}