package com.diets.weightloss.presentation.history

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.history_diet_activity.*
import kotlinx.coroutines.processNextEventInCurrentThread

class HistoryDietActivity : AppCompatActivity(R.layout.history_diet_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUI()
        bindSeekbars()

        setDifficulty(0)
        setGrade(4)
        sbDifficulty.progress = 0
        sbGrade.progress = 4
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

    private fun setUI() {
        cvTop.setBackgroundResource(R.drawable.card_history_shape)
    }
}