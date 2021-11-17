package com.diets.weightloss.presentation.history

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.common.db.entities.EASY_LEVEL
import com.diets.weightloss.common.db.entities.HARD_LEVEL
import com.diets.weightloss.common.db.entities.HistoryDiet
import com.diets.weightloss.common.db.entities.NORMAL_LEVEL
import com.diets.weightloss.presentation.history.dialogs.AttentionExitDialog
import com.diets.weightloss.presentation.history.dialogs.WeightAfterDialog
import com.diets.weightloss.presentation.history.dialogs.WeightUntilDialog
import com.diets.weightloss.utils.history.HistoryProvider
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
    }

    override fun saveAndExit() {
        saveDietHistory()
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
        historyDiet!!.weightAfter = HistoryProvider.convertTwoNumbersToFloat(kilo, gramm)
        tvAfterWeight.text = "${historyDiet!!.weightAfter} ${getString(R.string.kg_brok)}"
        setWeightDiff()
    }

    override fun changeUntilWeight(kilo: Int, gramm: Int) {
        historyDiet!!.weightUntil = HistoryProvider.convertTwoNumbersToFloat(kilo, gramm)
        tvUntilWeight.text = "${historyDiet!!.weightUntil} ${getString(R.string.kg_brok)}"
        setWeightDiff()
    }

    private fun setListeners() {
        if (isInteractive) {
            llWeightUntil.setOnClickListener {
                WeightUntilDialog
                        .newInstance(HistoryProvider.convertFloatToTwoNumbers(historyDiet!!.weightUntil))
                        .show(supportFragmentManager, "")
            }

            llWeightAfter.setOnClickListener {
                WeightAfterDialog
                        .newInstance(HistoryProvider.convertFloatToTwoNumbers(historyDiet!!.weightAfter))
                        .show(supportFragmentManager, "")
            }

        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

        btnSave.setOnClickListener {
            saveDietHistory()
        }
    }

    private fun saveDietHistory() {
        historyDiet!!.weightUntil = tvUntilWeight.text.toString().toFloat()
        historyDiet!!.weightAfter = tvAfterWeight.text.toString().toFloat()
        historyDiet!!.userDifficulty = sbDifficulty.progress
        historyDiet!!.satisfaction = sbGrade.progress
        historyDiet!!.comment = edtReview.text.toString()

        DBHolder.insertHistoryDietInDB(historyDiet!!)
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
        tvHardLevel.text = when (historyDiet!!.difficulty) {
            EASY_LEVEL -> {
                getString(R.string.easy_diff)
            }
            NORMAL_LEVEL -> {
                getString(R.string.med_diff_desc)
            }
            HARD_LEVEL -> {
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

        var formater = DecimalFormat("#0.00")

        when {
            historyDiet!!.weightUntil > historyDiet!!.weightAfter -> {
                tvDiffWeight.text = "${formater.format(historyDiet!!.weightUntil - historyDiet!!.weightAfter)} ${getString(R.string.kg_brok)}".replace(",", ".")
                tvDiffWeight.setTextColor(resources.getColor(R.color.decrease_weight))
                lavStatus.pauseAnimation()
                lavStatus.setAnimation("history_diff_decrease.json")
                lavStatus.rotation = 0.0f
                lavStatus.playAnimation()
                lavStatus.visibility = View.VISIBLE
            }
            historyDiet!!.weightUntil < historyDiet!!.weightAfter -> {
                tvDiffWeight.text = "${formater.format(historyDiet!!.weightAfter - historyDiet!!.weightUntil)} ${getString(R.string.kg_brok)}".replace(",", ".")
                tvDiffWeight.setTextColor(resources.getColor(R.color.increase_weight))
                lavStatus.pauseAnimation()
                lavStatus.setAnimation("history_diff_increase.json")
                lavStatus.rotation = 180.0f
                lavStatus.playAnimation()
                lavStatus.visibility = View.VISIBLE
            }
            else -> {
                tvDiffWeight.text = "${formater.format(historyDiet!!.weightAfter - historyDiet!!.weightUntil)} ${getString(R.string.kg_brok)}".replace(",", ".")
                tvDiffWeight.setTextColor(resources.getColor(R.color.pause_weight))
                lavStatus.pauseAnimation()
                lavStatus.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindHeadAnim() {
        if (historyDiet?.state == Const.COMPLETED_DIET) {
            lavWin.visibility = View.VISIBLE
            if (isInteractive) {
                lavWin.playAnimation()
                startAnimFireworks()
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

    private fun startAnimFireworks() {
        var counter = 0

        lavFirework.visibility = View.VISIBLE
        var alpha = ValueAnimator.ofFloat(lavFirework.alpha, 0.0f)
        alpha.duration = 2_000L
        alpha.addUpdateListener {
            lavFirework.alpha = it.animatedValue as Float

            if (it.animatedFraction == 1.0f) {
                lavFirework.pauseAnimation()
                lavFirework.visibility = View.GONE
            }
        }


        lavFirework.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
                counter++
                if (counter == 1) {
                    alpha.start()
                }
            }
        })

        lavFirework.playAnimation()
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