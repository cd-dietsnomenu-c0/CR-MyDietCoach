package com.meal.planner.presentation.water.controller.capacities

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.meal.planner.R
import kotlinx.android.synthetic.main.item_capacity.view.*

class CapacityVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iCapacityAdapter: ICapacityAdapter) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_capacity, viewGroup, false)), View.OnClickListener {

    val show: Animation
    val hide: Animation
    var iconId = -1

    init {
        itemView.setOnClickListener(this)
        show = AnimationUtils.loadAnimation(itemView.context, R.anim.alpha_capacity_active)
        hide = AnimationUtils.loadAnimation(itemView.context, R.anim.alpha_capacity_inactive)
    }

    override fun onClick(v: View?) {
        select()
        iCapacityAdapter.select(adapterPosition, -1)
    }

    fun bind(value: String, imgId: Int, isNeedSelect: Boolean) {
        iconId = imgId
        itemView.tvValueCapacity.text = value
        itemView.ivIconCapacity.setImageResource(imgId)
        if (isNeedSelect){
            itemView.ivBackCapacity.visibility = View.VISIBLE
            itemView.tvValueCapacity.setTextColor(itemView.resources.getColor(R.color.white))
            changeDrawableState(true)
        }else{
            itemView.ivBackCapacity.visibility = View.GONE
            itemView.tvValueCapacity.setTextColor(itemView.resources.getColor(R.color.active_water_blue))
            changeDrawableState(false)
        }
    }

    fun select() {
        hide.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                hide.setAnimationListener(null)
                itemView.tvValueCapacity.setTextColor(itemView.resources.getColor(R.color.white))
                changeDrawableState(true)
                itemView.ivBackCapacity.startAnimation(show)
                itemView.tvValueCapacity.startAnimation(show)
                itemView.ivIconCapacity.startAnimation(show)
                itemView.ivBackCapacity.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        itemView.tvValueCapacity.startAnimation(hide)
        itemView.ivIconCapacity.startAnimation(hide)
    }


    private fun changeDrawableState(isActive: Boolean) {
        var drawable = VectorDrawableCompat.create(
                itemView.resources,
                iconId, null
        ) as Drawable
        drawable = DrawableCompat.wrap(drawable)
        if (!isActive){
            DrawableCompat.setTint(drawable, itemView.resources.getColor(R.color.active_water_blue))
        }else{
            DrawableCompat.setTint(drawable, itemView.resources.getColor(R.color.white))
        }
        itemView.ivIconCapacity.setImageDrawable(drawable)
    }
}