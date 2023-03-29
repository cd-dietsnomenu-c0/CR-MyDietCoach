package com.diets.weightloss.presentation.profile.measurments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.measurments.dialogs.MarkDialog
import com.diets.weightloss.presentation.profile.measurments.toasts.RefreshToast
import com.diets.weightloss.utils.FieldsWorker
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.utils.water.WaterCounter
import com.diets.weightloss.utils.water.WaterRateProvider
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.load_ad_include.*
import kotlinx.android.synthetic.main.meas_activitys.*
import kotlinx.android.synthetic.main.meas_activitys.flAdContainer

class MeasActivity : AppCompatActivity(R.layout.meas_activitys) {

    var sexType = PreferenceProvider.SEX_TYPE_FEMALE
    var waterNormWithFactors = 0

    lateinit var markDialog: MarkDialog
    val MARK_DIALOG_TAG = "MARK_DIALOG_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edtName.setText(PreferenceProvider.getName()!!)
        if (PreferenceProvider.getWeight()!! != PreferenceProvider.EMPTY) {
            edtWeight.setText(PreferenceProvider.getWeight()!!.toString())
            waterNormWithFactors = WaterCounter.getWaterDailyRate(PreferenceProvider.getSex()!!, PreferenceProvider.getTrainingFactor()!!,
                    PreferenceProvider.getHotFactor()!!, PreferenceProvider.getWeight()!!, false)
            edtWater.setText(WaterCounter.getWaterDailyRate(PreferenceProvider.getSex()!!, false,
                    false, PreferenceProvider.getWeight()!!, false).toString())
        }

        bindGenderViews()
        bindDefaultRateViews()


        ivBack.setOnClickListener {
            onBackPressed()
        }

        btnSave.setOnClickListener {
            checkFieldAndSave()
        }

        tvMark.setOnClickListener {
            markDialog = MarkDialog()
            markDialog.show(supportFragmentManager, MARK_DIALOG_TAG)
        }

        edtWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (swtDefaultRate.isChecked) {
                    if (FieldsWorker.isCorrect(edtWeight.text.toString())) {
                        calculateWaterRate()
                    } else {
                        edtWater.setText("0")
                    }
                }
            }
        })


        prepareAnimViews()

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

    private fun bindDefaultRateViews() {
        if (PreferenceProvider.getWaterRateChangedManual() == PreferenceProvider.EMPTY) {
            swtDefaultRate.isChecked = true
            turnOffWaterEdit()
        } else {
            swtDefaultRate.isChecked = false
            turnOnWaterEdit()
        }

        swtDefaultRate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                turnOffWaterEdit()
            } else {
                turnOnWaterEdit()
            }
        }

    }

    private fun turnOnWaterEdit() {
        edtWater.isEnabled = true
        edtWater.setTextColor(resources.getColor(R.color.black))
    }

    private fun turnOffWaterEdit() {
        edtWater.isEnabled = false
        edtWater.setTextColor(resources.getColor(R.color.label_color))
        if (edtWeight.text.toString() != "" && edtWeight.text.toString() != " ") {
            edtWater.setText(WaterCounter.countRate(sexType, false, false, edtWeight.text.toString().toInt()).toString())
        } else {
            edtWater.setText("0")
        }
    }

    private fun bindGenderViews() {
        if (PreferenceProvider.getSex()!! == PreferenceProvider.SEX_TYPE_MALE) {
            selectMale()
        } else {
            selectFemale()
        }
    }

    private fun selectFemale() {
        lavTickFemale.visibility = View.VISIBLE
        lavTickFemale.frame = 40
        sexType = PreferenceProvider.SEX_TYPE_FEMALE
    }

    private fun selectMale() {
        lavTickMale.visibility = View.VISIBLE
        lavTickMale.frame = 40
        sexType = PreferenceProvider.SEX_TYPE_MALE
    }

    private fun checkFieldAndSave() {
        if (FieldsWorker.isCorrect(edtWeight.text.toString())) {
            if (FieldsWorker.isCorrect(edtWater.text.toString()) && edtWater.text.toString() != "0") {
                var weight = edtWeight.text.toString().toInt()
                if (weight in 21..199) {
                    PreferenceProvider.setWeight(weight)
                    PreferenceProvider.setSex(sexType)
                    if (!swtDefaultRate.isChecked) {
                        PreferenceProvider.setWaterRateChangedManual(edtWater.text.toString().toInt())
                        WaterRateProvider.addNewRate(edtWater.text.toString().toInt())
                    } else {
                        PreferenceProvider.setWaterRateChangedManual(PreferenceProvider.EMPTY)
                        WaterRateProvider.addNewRate(waterNormWithFactors)
                    }

                    if (FieldsWorker.isCorrect(edtName.text.toString())) {
                        PreferenceProvider.setName(edtName.text.toString())
                    }
                    RefreshToast.show(this)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.input_error_not_in_limit), Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, getString(R.string.water_rate_error), Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, getString(R.string.input_error_empty), Toast.LENGTH_LONG).show()
        }
    }


    private fun prepareAnimViews() {
        llFemale.setOnClickListener {
            lavTickFemale.frame = 0
            lavTickFemale.visibility = View.VISIBLE
            lavTickFemale.playAnimation()

            lavTickMale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_FEMALE
            if (swtDefaultRate.isChecked && FieldsWorker.isCorrect(edtWeight.text.toString())) {
                calculateWaterRate()
            }
        }

        llMale.setOnClickListener {
            lavTickMale.frame = 0
            lavTickMale.visibility = View.VISIBLE
            lavTickMale.playAnimation()

            lavTickFemale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_MALE
            if (swtDefaultRate.isChecked && FieldsWorker.isCorrect(edtWeight.text.toString())) {
                calculateWaterRate()
            }
        }
    }

    private fun calculateWaterRate() {
        waterNormWithFactors = WaterCounter.countRate(sexType, PreferenceProvider.getTrainingFactor()!!, PreferenceProvider.getHotFactor()!!, edtWeight.text.toString().toInt())
        edtWater.setText(WaterCounter.countRate(sexType, false, false, edtWeight.text.toString().toInt()).toString())
    }
}