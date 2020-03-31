package com.wsoteam.mydietcoach.diets.items.article.interactive.controller.managers

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearSmoothScroller

class TopSnappedScroller(context: Context) : LinearSmoothScroller(context) {

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}