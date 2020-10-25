package com.jundev.weightloss.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.jundev.weightloss.R

class FixedSeekbar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : AppCompatSeekBar(context, attrs, defStyle) {
    private var mTickMark: Drawable? = null

    init {
        applyAttributes(attrs, defStyle)
    }

    private fun applyAttributes(rawAttrs: AttributeSet?, defStyleAttr: Int) {
        val attrs = context.obtainStyledAttributes(rawAttrs, R.styleable.CustomSeekBar, defStyleAttr, 0)
        try {
            mTickMark = attrs.getDrawable(R.styleable.CustomSeekBar_tickMarkFixed)
        } finally {
            attrs.recycle()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTickMarks(canvas)
    }


    private fun drawTickMarks(canvas: Canvas) {
        if (mTickMark != null) {
            val count = max

            val w = mTickMark!!.intrinsicWidth
            val h = mTickMark!!.intrinsicHeight
            val halfThumbW = thumb.intrinsicWidth / 2
            val halfW = if (w >= 0) w / 2 else 1
            val halfH = if (h >= 0) h / 2 else 1
            mTickMark!!.setBounds(-halfW, -halfH, halfW, halfH)
            val spacing = (width - paddingLeft - paddingRight + thumbOffset * 2 - halfThumbW * 2) / count.toFloat()
            val saveCount = canvas.save()
            canvas.translate((paddingLeft - thumbOffset + halfThumbW).toFloat(), (height / 2).toFloat())
            for (i in 0..count) {
                if (i != progress)
                    mTickMark!!.draw(canvas)
                canvas.translate(spacing, 0F)
            }
            canvas.restoreToCount(saveCount)
        }
    }
}