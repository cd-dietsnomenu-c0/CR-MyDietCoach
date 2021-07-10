package com.diets.weightloss.presentation.water.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.notifications.dialogs.DaysDialog
import com.diets.weightloss.presentation.water.notifications.dialogs.EndDialog
import com.diets.weightloss.presentation.water.notifications.dialogs.FrequentDialog
import com.diets.weightloss.presentation.water.notifications.dialogs.StartDialog
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.workers.FrequentWorker
import com.diets.weightloss.utils.workers.TimeNotifWorker
import kotlinx.android.synthetic.main.notification_settings_activity.*

class NotificationSettingActivity : AppCompatActivity(R.layout.notification_settings_activity), StartDialog.Callbacks, EndDialog.Callbacks, FrequentDialog.Callbacks {

    lateinit var frequentDialog: FrequentDialog
    var FREQUENT_TAG_DIALOG = "FREQUENT_TAG_DIALOG"

    lateinit var daysDialog: DaysDialog
    var DAYS_TAG_DIALOG = "DAYS_TAG_DIALOG"

    lateinit var startDialog: StartDialog
    var START_TAG_DIALOG = "START_TAG_DIALOG"

    lateinit var endDialog: EndDialog
    var END_TAG_DIALOG = "END_TAG_DIALOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDialogs()
        setOnClickListeners()
        bindValues()
    }

    private fun bindValues() {
        swNotif.isChecked = PreferenceProvider.isTurnOnWaterNotifications
        swAfterNorm.isChecked = PreferenceProvider.isTurnOnAfterWaterNorm
        swRecently.isChecked = PreferenceProvider.isTurnOnRecentlyWater

        tvStart.text = PreferenceProvider.startWaterNotifTime
        tvEnd.text = PreferenceProvider.endWaterNotifTime

        tvFrequent.text = FrequentWorker.getReadableFrequent()
    }

    private fun bindDialogs() {
        daysDialog = DaysDialog.newInstance(0)
    }

    private fun setOnClickListeners() {
        flStart.setOnClickListener {
            startDialog = StartDialog.newInstance(TimeNotifWorker.getStartHour(), TimeNotifWorker.getStartMinute())
            startDialog.show(supportFragmentManager, START_TAG_DIALOG)
        }

        flEnd.setOnClickListener {
            endDialog = EndDialog.newInstance(TimeNotifWorker.getEndHour(), TimeNotifWorker.getEndMinute())
            endDialog.show(supportFragmentManager, END_TAG_DIALOG)
        }

        flFrequent.setOnClickListener {
            frequentDialog = FrequentDialog.newInstance(PreferenceProvider.frequentNotificationsType)
            frequentDialog.show(supportFragmentManager, FREQUENT_TAG_DIALOG)
        }

        flDays.setOnClickListener {
            daysDialog.show(supportFragmentManager, DAYS_TAG_DIALOG)
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }


        swNotif.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnWaterNotifications = isChecked
        }

        swAfterNorm.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnAfterWaterNorm = isChecked
        }

        swRecently.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnRecentlyWater = isChecked
        }
    }

    override fun changeStartTime(hour: Int, minute: Int) {
        tvStart.text = TimeNotifWorker.setStartTime(hour, minute)
    }

    override fun changeEndTime(hour: Int, minute: Int) {
        tvEnd.text = TimeNotifWorker.setEndTime(hour, minute)
    }

    override fun changeFrequent(indexType: Int) {
        PreferenceProvider.frequentNotificationsType = indexType
        tvFrequent.text = FrequentWorker.getReadableFrequent()
    }
}