package com.diets.weightloss.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import kotlinx.android.synthetic.main.history_diet_activity.*

class HistoryDietActivity : AppCompatActivity(R.layout.history_diet_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUI()
    }

    private fun setUI() {
        cvTop.setBackgroundResource(R.drawable.card_history_shape)
    }
}