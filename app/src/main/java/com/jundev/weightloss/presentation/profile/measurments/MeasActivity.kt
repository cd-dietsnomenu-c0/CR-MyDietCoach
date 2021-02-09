package com.jundev.weightloss.presentation.profile.measurments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.meas_activity.*

class MeasActivity : AppCompatActivity(R.layout.meas_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}