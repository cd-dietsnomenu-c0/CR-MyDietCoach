package com.diets.weightloss.presentation.profile.measurments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.R
import com.diets.weightloss.presentation.profile.measurments.toasts.RefreshToast
import com.diets.weightloss.utils.FieldsWorker
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.water.WaterCounter
import com.diets.weightloss.utils.water.WaterRateProvider
import kotlinx.android.synthetic.main.meas_activitys.*

class MeasActivity : AppCompatActivity(R.layout.meas_activity) {

    var sexType = PreferenceProvider.SEX_TYPE_FEMALE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edtName.setText(PreferenceProvider.getName()!!)
        if (PreferenceProvider.getWeight()!! != PreferenceProvider.EMPTY){
            edtWeight.setText(PreferenceProvider.getWeight()!!.toString())
            edtWater.setText(WaterCounter.getWaterDailyRate(PreferenceProvider.getSex()!!, PreferenceProvider.getTrainingFactor()!!,
                    PreferenceProvider.getHotFactor()!!, PreferenceProvider.getWeight()!!, false).toString())
        }

        bindGenderViews()
        bindDefaultRateViews()


        ivBack.setOnClickListener {
            onBackPressed()
        }

        btnSave.setOnClickListener {
            checkFieldAndSave()
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
            edtWater.setText(WaterCounter.countRate(sexType, PreferenceProvider.getTrainingFactor()!!, PreferenceProvider.getHotFactor()!!, edtWeight.text.toString().toInt()).toString())
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
                    if (!swtDefaultRate.isChecked){
                        PreferenceProvider.setWaterRateChangedManual(edtWater.text.toString().toInt())
                    }else{
                        PreferenceProvider.setWaterRateChangedManual(PreferenceProvider.EMPTY)
                    }
                    WaterRateProvider.addNewRate(edtWater.text.toString().toInt())
                    if (FieldsWorker.isCorrect(edtName.text.toString())){
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
            if (swtDefaultRate.isChecked) {
                calculateWaterRate()
            }
        }

        llMale.setOnClickListener {
            lavTickMale.frame = 0
            lavTickMale.visibility = View.VISIBLE
            lavTickMale.playAnimation()

            lavTickFemale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_MALE
            if (swtDefaultRate.isChecked) {
                calculateWaterRate()
            }
        }
    }

    private fun calculateWaterRate() {
        edtWater.setText(WaterCounter.countRate(sexType, PreferenceProvider.getTrainingFactor()!!, PreferenceProvider.getHotFactor()!!, edtWeight.text.toString().toInt()).toString())
    }
}