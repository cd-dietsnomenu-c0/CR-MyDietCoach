package com.jundev.weightloss.diets.items.article.interactive.controller.managers

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LayoutManagerTopScroll(context: Context) : LinearLayoutManager(context, VERTICAL, false) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val scroller = TopSnappedScroller(recyclerView!!.context)
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }
}