package com.diets.weightloss.presentation.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.common.db.entities.HistoryDiet
import kotlinx.android.synthetic.main.history_diet_activity.*
import kotlinx.coroutines.processNextEventInCurrentThread

class HistoryDietActivity : AppCompatActivity(R.layout.history_diet_activity) {

    private var historyDiet: HistoryDiet? = null
    private var isNeedShowAnim = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyDiet = intent.getSerializableExtra(TAG_HISTORY_DIET) as HistoryDiet
        isNeedShowAnim = intent.getBooleanExtra(TAG_INTERACTIVE_STATE, false)

        cvTop.setBackgroundResource(R.drawable.card_history_shape)
        updateUI()

        setDifficulty(0)
        setGrade(4)
        sbDifficulty.progress = 0
        sbGrade.progress = 4
    }

    private fun updateUI() {
        bindSeekbars()
        bindHeadAnim()
        Glide.with(this).load(historyDiet!!.imageUrl).into(ivHead)
        tvName.text = historyDiet!!.name
        tvStart.text = historyDiet!!.readableStart
        tvEnd.text = historyDiet!!.readableEnd
        tvTime.text = resources.getQuantityString(R.plurals.days_plur, historyDiet!!.readablePeriod, historyDiet!!.readablePeriod)
        tvLostDays.text = resources.getQuantityString(R.plurals.lifes_plur, historyDiet!!.loseLifes, historyDiet!!.loseLifes)
        tvDifficulty.text = when (historyDiet!!.difficulty) {
            Const.EASY_LEVEL -> {
                getString(R.string.easy_diff)
            }
            Const.NORMAL_LEVEL -> {
                getString(R.string.med_diff_desc)
            }
            Const.HARD_LEVEL -> {
                getString(R.string.hard_diff)
            }
            else -> {
                getString(R.string.med_diff_desc)
            }
        }
    }

    private fun bindHeadAnim() {
        if (historyDiet?.state == Const.COMPLETED_DIET) {
            lavWin.visibility = View.VISIBLE
            if (isNeedShowAnim) {
                lavWin.playAnimation()
            } else {
                lavWin.progress = 1.0f
            }
        } else {
            lavLose.visibility = View.VISIBLE
            if (isNeedShowAnim) {
                lavLose.playAnimation()
            } else {
                lavLose.progress = 1.0f
            }
        }
    }


    private fun bindSeekbars() {
        sbDifficulty.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setDifficulty(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        sbGrade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setGrade(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setGrade(progress: Int) {
        tvGrade.text = resources.getStringArray(R.array.text_grade)[progress]
        ivGrade.setImageResource(resources.obtainTypedArray(R.array.imgs_grade).getResourceId(progress, -1))
    }

    private fun setDifficulty(progress: Int) {
        tvDifficulty.text = resources.getStringArray(R.array.text_diff)[progress]
        ivDifficulty.setImageResource(resources.obtainTypedArray(R.array.imgs_diff).getResourceId(progress, -1))
    }


    companion object {
        private const val TAG_HISTORY_DIET = "TAG_HISTORY_DIET"
        private const val TAG_INTERACTIVE_STATE = "TAG_INTERACTIVE_STATE"

        fun getIntent(context: Context, historyDiet: HistoryDiet, isInteractive: Boolean): Intent {
            var intent = Intent(context, HistoryDietActivity::class.java)
            intent.putExtra(TAG_HISTORY_DIET, historyDiet)
            intent.putExtra(TAG_INTERACTIVE_STATE, isInteractive)
            return intent
        }
    }
}