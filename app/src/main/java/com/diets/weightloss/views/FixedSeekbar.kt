package com.diets.weightloss.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.diets.weightloss.R

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

            var textPaint = Paint()
            textPaint.color = Color.rgb(0, 0, 0)
            textPaint.textSize = 34f

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
                canvas.save()
                canvas.rotate(90f, textPaint.strokeWidth / 2, -h.toFloat() * 2.5f)
                canvas.drawText("${50 + i * 50} мл", -w.toFloat(), -h.toFloat() * 2.5f, textPaint)
                canvas.restore()
                if (i != progress) {
                    canvas.save()
                    canvas.rotate(90f)
                    mTickMark!!.draw(canvas)
                    canvas.restore()
                }
                canvas.translate(spacing, 0F)

            }
            canvas.restoreToCount(saveCount)
        }
    }
}