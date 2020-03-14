package com.wsoteam.mydietcoach.diets.items.article.interactive.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.Diet

class DietAdapter(var diet : Diet) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    val INFO_TYPE = 0
    val BENEFITS_TYPE = 1
    val CONS_TYPE = 2
    val MENU_TYPE = 3
    val RESULTS_TYPE = 4
    val REVIEW_TYPE = 5


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            INFO_TYPE -> InfoVh(inflater, parent)
            BENEFITS_TYPE -> BenefitsVH(inflater, parent)
            CONS_TYPE -> ConsVH(inflater, parent)
            MENU_TYPE -> MenuVH(inflater, parent)
            RESULTS_TYPE -> ResultsVH(inflater, parent)
            REVIEW_TYPE -> ReviewVH(inflater, parent)
            else -> InfoVh(inflater, parent)
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position){
            INFO_TYPE -> (holder as InfoVh).bind(diet.title, diet.introduction)
            BENEFITS_TYPE -> (holder as BenefitsVH).bind(diet.benefitsTitle, diet.benefits)
            CONS_TYPE -> (holder as ConsVH).bind(diet.consTitle, diet.cons)
            MENU_TYPE -> (holder as MenuVH).bind(diet.menuTitle, diet.menuText, diet.days, diet.hintText)
            RESULTS_TYPE -> (holder as ResultsVH).bind(diet.resultText)
            REVIEW_TYPE -> (holder as ReviewVH).bind(diet.review)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}