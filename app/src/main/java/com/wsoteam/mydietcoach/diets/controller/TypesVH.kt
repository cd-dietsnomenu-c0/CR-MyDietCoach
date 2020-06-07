package com.wsoteam.mydietcoach.diets.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.schema.Schema
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.IClick
import kotlinx.android.synthetic.main.vh_types.view.*

class TypesVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, val iClick: IClick) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.vh_types, viewGroup, false)), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        iClick.click(adapterPosition)
    }

    fun bind(schema: Schema) {
        Glide.with(itemView.context).load(schema.headImage).into(itemView.ivHead)
        itemView.tvTitle.text = schema.title
        setHardLevel(schema.hardLevel)
        if (schema.isOld) {
            setOldVH(schema)
        } else {
            setNewVH(schema)
        }
    }

    private fun setNewVH(schema: Schema) {
        if (schema.isHasTracker) {
            Glide.with(itemView.context).load(R.drawable.types_checked).into(itemView.ivTracker)
        } else {
            Glide.with(itemView.context).load(R.drawable.types_unchecked).into(itemView.ivTracker)
        }
        if (schema.isHasMenu) {
            Glide.with(itemView.context).load(R.drawable.types_checked).into(itemView.ivMenu)
        } else {
            Glide.with(itemView.context).load(R.drawable.types_unchecked).into(itemView.ivMenu)
        }
        itemView.tvDietCount.text = "${schema.reverseDietIndexes.size} шт"
    }

    private fun setOldVH(schema: Schema) {
        Glide.with(itemView.context).load(R.drawable.types_unchecked).into(itemView.ivTracker)
        Glide.with(itemView.context).load(R.drawable.types_unchecked).into(itemView.ivMenu)
        itemView.tvDietCount.text = "${schema.countOldDiets} шт"
    }

    private fun setHardLevel(hardLevel: Int) {
        when (hardLevel) {
            0 -> {
                itemView.ivSecondHard.visibility = View.INVISIBLE
                itemView.ivThirdHard.visibility = View.INVISIBLE
            }
            1 -> {
                itemView.ivSecondHard.visibility = View.VISIBLE
                itemView.ivThirdHard.visibility = View.INVISIBLE
            }
            2 -> {
                itemView.ivSecondHard.visibility = View.VISIBLE
                itemView.ivThirdHard.visibility = View.VISIBLE
            }
        }
    }
}