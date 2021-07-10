package com.diets.weightloss.presentation.water.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.notifications.dialogs.DaysDialog
import com.diets.weightloss.presentation.water.notifications.dialogs.FrequentDialog
import com.diets.weightloss.presentation.water.notifications.dialogs.StartDialog
import kotlinx.android.synthetic.main.notification_settings_activity.*

class NotificationSettingActivity : AppCompatActivity(R.layout.notification_settings_activity) {

    lateinit var frequentDialog: FrequentDialog
    var FREQUENT_TAG_DIALOG = "FREQUENT_TAG_DIALOG"

    lateinit var daysDialog: DaysDialog
    var DAYS_TAG_DIALOG = "DAYS_TAG_DIALOG"

    lateinit var startDialog: StartDialog
    var START_TAG_DIALOG = "START_TAG_DIALOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDialogs()
        setOnClickListeners()
    }

    private fun bindDialogs() {
        frequentDialog = FrequentDialog.newInstance(0)
        daysDialog = DaysDialog.newInstance(0)
        startDialog = StartDialog.newInstance(0)
    }

    private fun setOnClickListeners() {
        flStart.setOnClickListener {
            startDialog.show(supportFragmentManager, START_TAG_DIALOG)
        }

        flEnd.setOnClickListener {

        }

        flFrequent.setOnClickListener {
            frequentDialog.show(supportFragmentManager, FREQUENT_TAG_DIALOG)
        }

        flDays.setOnClickListener {
            daysDialog.show(supportFragmentManager, DAYS_TAG_DIALOG)
        }

        ivBack.setOnClickListener {

        }
    }


}