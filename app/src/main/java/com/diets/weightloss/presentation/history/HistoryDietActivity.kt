package com.diets.weightloss.presentation.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.presentation.history.dialogs.AttentionExitDialog
import com.diets.weightloss.presentation.history.dialogs.WeightAfterDialog
import com.diets.weightloss.presentation.history.dialogs.WeightUntilDialog
import com.diets.weightloss.utils.history.HistoryFormatter
import kotlinx.android.synthetic.main.fr_types.*
import kotlinx.android.synthetic.main.history_diet_activity.*
import java.text.DecimalFormat

class HistoryDietActivity : AppCompatActivity(R.layout.history_diet_activity), WeightAfterDialog.Callbacks, WeightUntilDialog.Callbacks, AttentionExitDialog.Callbacks {

    private var historyDiet: HistoryDiet? = null
    private var isInteractive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyDiet = intent.getSerializableExtra(TAG_HISTORY_DIET) as HistoryDiet
        isInteractive = intent.getBooleanExtra(TAG_INTERACTIVE_STATE, false)

        cvTop.setBackgroundResource(R.drawable.card_history_shape)
        updateUI()
        setListeners()

        setDifficulty(0)
        setGrade(4)
        sbDifficulty.progress = 0
        sbGrade.progress = 4
    }

    override fun saveAndExit() {
        finish()
    }

    override fun onBackPressed() {
        if (isInteractive) {
            AttentionExitDialog().show(supportFragmentManager, "")
        } else {
            super.onBackPressed()
        }
    }

    override fun changeAfterWeight(kilo: Int, gramm: Int) {
        historyDiet!!.weightAfter = HistoryFormatter.convertTwoNumbersToFloat(kilo, gramm)
        tvAfterWeight.text = "${historyDiet!!.weightAfter} ${getString(R.string.kg_brok)}"
        setWeightDiff()
    }

    override fun changeUntilWeight(kilo: Int, gramm: Int) {
        historyDiet!!.weightUntil = HistoryFormatter.convertTwoNumbersToFloat(kilo, gramm)
        tvUntilWeight.text = "${historyDiet!!.weightUntil} ${getString(R.string.kg_brok)}"
        setWeightDiff()
    }

    private fun setListeners() {
        if (isInteractive) {
            llWeightUntil.setOnClickListener {
                WeightUntilDialog
                        .newInstance(HistoryFormatter.convertFloatToTwoNumbers(historyDiet!!.weightUntil))
                        .show(supportFragmentManager, "")
            }

            llWeightAfter.setOnClickListener {
                WeightAfterDialog
                        .newInstance(HistoryFormatter.convertFloatToTwoNumbers(historyDiet!!.weightAfter))
                        .show(supportFragmentManager, "")
            }

        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
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

        edtReview.isEnabled = isInteractive

        setDifficulty(historyDiet!!.userDifficulty)
        sbDifficulty.progress = historyDiet!!.userDifficulty

        setGrade(historyDiet!!.satisfaction)
        sbGrade.progress = historyDiet!!.satisfaction

        tvUntilWeight.text = "${historyDiet!!.weightUntil} ${getString(R.string.kg_brok)}"
        tvAfterWeight.text = "${historyDiet!!.weightAfter} ${getString(R.string.kg_brok)}"

        if (!isInteractive) {
            ivEditAfter.visibility = View.INVISIBLE
            ivEditUntil.visibility = View.INVISIBLE
            btnSave.visibility = View.GONE
        }

        setWeightDiff()

    }

    private fun setWeightDiff() {
        var isDecrease = historyDiet!!.weightUntil > historyDiet!!.weightAfter

        var formater = DecimalFormat("#0.00")

        if (isDecrease) {
            tvDiffWeight.text = "${formater.format(historyDiet!!.weightUntil - historyDiet!!.weightAfter)} ${getString(R.string.kg_brok)}"
            tvDiffWeight.setTextColor(resources.getColor(R.color.decrease_weight))
            lavStatus.pauseAnimation()
            lavStatus.setAnimation("history_diff_decrease.json")
            lavStatus.rotation = 0.0f
            lavStatus.playAnimation()
        } else {
            tvDiffWeight.text = "${formater.format(historyDiet!!.weightAfter - historyDiet!!.weightUntil)} ${getString(R.string.kg_brok)}"
            tvDiffWeight.setTextColor(resources.getColor(R.color.increase_weight))
            lavStatus.pauseAnimation()
            lavStatus.setAnimation("history_diff_increase.json")
            lavStatus.rotation = 180.0f
            lavStatus.playAnimation()
        }
    }

    private fun bindHeadAnim() {
        if (historyDiet?.state == Const.COMPLETED_DIET) {
            lavWin.visibility = View.VISIBLE
            if (isInteractive) {
                lavWin.playAnimation()
            } else {
                lavWin.progress = 1.0f
            }
        } else {
            lavLose.visibility = View.VISIBLE
            if (isInteractive) {
                lavLose.playAnimation()
            } else {
                lavLose.progress = 1.0f
            }
        }
    }


    private fun bindSeekbars() {
        sbDifficulty.isEnabled = isInteractive
        sbGrade.isEnabled = isInteractive

        if (isInteractive) {
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