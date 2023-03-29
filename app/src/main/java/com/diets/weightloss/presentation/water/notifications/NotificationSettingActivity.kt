package com.diets.weightloss.presentation.water.notifications

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.water.notifications.dialogs.*
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.utils.notif.services.TopicWorker
import com.diets.weightloss.utils.water.workers.DaysWorkers
import com.diets.weightloss.utils.water.workers.FrequentWorker
import com.diets.weightloss.utils.water.workers.TimeNotifWorker
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.meas_activitys.*
import kotlinx.android.synthetic.main.notification_settings_activity.*
import kotlinx.android.synthetic.main.notification_settings_activity.flAdContainer
import kotlinx.android.synthetic.main.notification_settings_activity.ivBack

class NotificationSettingActivity : AppCompatActivity(R.layout.notification_settings_activity),
        StartDialog.Callbacks, EndDialog.Callbacks, FrequentDialog.Callbacks, DaysDialog.Callbacks {

    lateinit var frequentDialog: FrequentDialog
    var FREQUENT_TAG_DIALOG = "FREQUENT_TAG_DIALOG"

    lateinit var daysDialog: DaysDialog
    var DAYS_TAG_DIALOG = "DAYS_TAG_DIALOG"

    lateinit var startDialog: StartDialog
    var START_TAG_DIALOG = "START_TAG_DIALOG"

    lateinit var endDialog: EndDialog
    var END_TAG_DIALOG = "END_TAG_DIALOG"

    lateinit var afterNormDialog: AfterNormDialog
    var AFTER_NORM_TAG_DIALOG = "AFTER_NORM_TAG_DIALOG"

    lateinit var recentlyIntakeDialog: RecentlyIntakeDialog
    var RECENT_INTAKE_TAG_DIALOG = "RECENT_INTAKE_TAG_DIALOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
        bindValues()

        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                if (nativeList.size > 0) {
                    loadNative(nativeList[0])
                }
            }
        })
    }

    private fun loadNative(nativeAd: NativeAd) {
        ad_view.mediaView = ad_media
        ad_view.headlineView = ad_headline
        ad_view.bodyView = ad_body
        ad_view.callToActionView = ad_call_to_action
        ad_view.iconView = ad_icon
        (ad_view.headlineView as TextView).text = nativeAd.headline
        (ad_view.bodyView as TextView).text = nativeAd.body
        (ad_view.callToActionView as Button).text = nativeAd.callToAction
        val icon = nativeAd.icon
        if (icon == null) {
            ad_view.iconView!!.visibility = View.INVISIBLE
        } else {
            (ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            ad_view.iconView!!.visibility = View.VISIBLE
        }
        ad_view.setNativeAd(nativeAd)

        flAdContainer.visibility = View.VISIBLE
    }

    private fun bindValues() {
        swNotif.isChecked = PreferenceProvider.isTurnOnWaterNotifications
        swAfterNorm.isChecked = PreferenceProvider.isTurnOnAfterWaterNorm
        swRecently.isChecked = PreferenceProvider.isTurnOnRecentlyWater

        tvStart.text = PreferenceProvider.startWaterNotifTime
        tvEnd.text = PreferenceProvider.endWaterNotifTime

        tvFrequent.text = FrequentWorker.getReadableFrequent()
        tvFrequentDays.text = DaysWorkers.getReadableDays()
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
            daysDialog = DaysDialog.newInstance(PreferenceProvider.daysNotificationsType)
            daysDialog.show(supportFragmentManager, DAYS_TAG_DIALOG)
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }


        swNotif.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnWaterNotifications = isChecked
            TopicWorker.changeWaterNotifState(isChecked)
        }

        swAfterNorm.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnAfterWaterNorm = isChecked
        }

        swRecently.setOnCheckedChangeListener { _, isChecked ->
            PreferenceProvider.isTurnOnRecentlyWater = isChecked
        }

        tvLabelNorm.setOnClickListener {
            afterNormDialog = AfterNormDialog()
            afterNormDialog.show(supportFragmentManager, AFTER_NORM_TAG_DIALOG)
        }

        tvLabelRecent.setOnClickListener {
            recentlyIntakeDialog = RecentlyIntakeDialog()
            recentlyIntakeDialog.show(supportFragmentManager, RECENT_INTAKE_TAG_DIALOG)
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

    override fun changeDays(states: List<Boolean>) {
        DaysWorkers.saveDaysStates(states)
        tvFrequentDays.text = DaysWorkers.getReadableDays()
    }
}