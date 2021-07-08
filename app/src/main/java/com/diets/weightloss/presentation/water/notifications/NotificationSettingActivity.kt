package com.diets.weightloss.presentation.water.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.notifications.dialogs.FrequentDialog
import kotlinx.android.synthetic.main.notification_settings_activity.*

class NotificationSettingActivity : AppCompatActivity(R.layout.notification_settings_activity) {

    lateinit var frequentDialog: FrequentDialog
    var FREQUENT_TAG_DIALOG = "FREQUENT_TAG_DIALOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDialogs()
        setOnClickListeners()
    }

    private fun bindDialogs() {
        frequentDialog = FrequentDialog.newInstance(0)

    }

    private fun setOnClickListeners() {
        flStart.setOnClickListener {

        }

        flEnd.setOnClickListener {

        }

        flFrequent.setOnClickListener {
            frequentDialog.show(supportFragmentManager, FREQUENT_TAG_DIALOG)
        }

        flDays.setOnClickListener {

        }

        ivBack.setOnClickListener {

        }
    }


}