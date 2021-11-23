package com.diets.weightloss.presentation.history

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.diets.weightloss.App
import com.diets.weightloss.Const
import com.diets.weightloss.R
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.common.db.entities.*
import com.diets.weightloss.presentation.history.dialogs.AttentionExitDialog
import com.diets.weightloss.presentation.history.dialogs.WeightAfterDialog
import com.diets.weightloss.presentation.history.dialogs.WeightUntilDialog
import com.diets.weightloss.presentation.history.toasts.SaveToast
import com.diets.weightloss.utils.ad.AdWorker
import com.diets.weightloss.utils.ad.NativeSpeaker
import com.diets.weightloss.utils.history.HistoryProvider
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.android.synthetic.main.history_ad_include.*
import kotlinx.android.synthetic.main.history_ad_include.ad_view
import kotlinx.android.synthetic.main.history_diet_activity.*
import kotlinx.android.synthetic.main.history_second_ad_include.*
import java.text.DecimalFormat

private const val WEIGHT_INCREASE = 1
private const val WEIGHT_DECREASE = -1
private const val WEIGHT_NOT_CHANGE = 0

class HistoryDietActivity : AppCompatActivity(R.layout.history_diet_activity), WeightAfterDialog.Callbacks, WeightUntilDialog.Callbacks, AttentionExitDialog.Callbacks {

    private var historyDiet: HistoryDiet? = null
    private var isInteractive = false

    private var weightDiffState = WEIGHT_NOT_CHANGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyDiet = intent.getSerializableExtra(TAG_HISTORY_DIET) as HistoryDiet
        isInteractive = intent.getBooleanExtra(TAG_INTERACTIVE_STATE, false)

        cvTop.setBackgroundResource(R.drawable.card_history_shape)
        updateUI()
        setListeners()

        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                if (nativeList.size > 0) {
                    loadFirstNative(nativeList[0])
                    if (nativeList.size > 1){
                        loadSecondNative(nativeList[1])
                    }else{
                        loadSecondNative(nativeList[0])
                    }
                }
            }
        })
    }

    private fun loadFirstNative(nativeAd: UnifiedNativeAd) {
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
            ad_view.iconView.visibility = View.INVISIBLE
        } else {
            (ad_view.iconView as ImageView).setImageDrawable(icon.drawable)
            ad_view.iconView.visibility = View.VISIBLE
        }
        ad_view.setNativeAd(nativeAd)
        flAdContainer.visibility = View.VISIBLE
    }

    private fun loadSecondNative(nativeAd: UnifiedNativeAd) {
        ad_view_second.mediaView = ad_media_second
        ad_view_second.headlineView = ad_headline_second
        ad_view_second.bodyView = ad_body_second
        ad_view_second.callToActionView = ad_call_to_action_second
        ad_view_second.iconView = ad_icon_second
        (ad_view_second.headlineView as TextView).text = nativeAd.headline
        (ad_view_second.bodyView as TextView).text = nativeAd.body
        (ad_view_second.callToActionView as Button).text = nativeAd.callToAction
        val icon = nativeAd.icon
        if (icon == null) {
            ad_view_second.iconView.visibility = View.INVISIBLE
        } else {
            (ad_view_second.iconView as ImageView).setImageDrawable(icon.drawable)
            ad_view_second.iconView.visibility = View.VISIBLE
        }
        ad_view_second.setNativeAd(nativeAd)
        flAdContainerSecond.visibility = View.VISIBLE
    }

    override fun saveAndExit() {
        saveDietHistory()
        SaveToast.show(this)
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
            saveAndExit()
        }

        ivShare.setOnClickListener {
            share(getReview())
        }
    }

    private fun share(review: String) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, review + "\n"
                + "https://play.google.com/store/apps/details?id=${App.getInstance().packageName}")
        startActivity(intent)
    }

    private fun getReview(): String {
        return if (historyDiet!!.state == COMPLETED_DIET) {
            getString(R.string.review_history_completed, tvName.text, tvDifficulty.text.toString().toLowerCase(), tvGrade.text, getWeightDiff())
        } else {
            getString(R.string.review_history_uncompleted, tvName.text, tvDifficulty.text.toString().toLowerCase(), tvGrade.text, getWeightDiff())
        }
    }

    private fun getWeightDiff(): String {
        return when (weightDiffState) {
            WEIGHT_NOT_CHANGE -> getString(R.string.weight_not_change)
            WEIGHT_INCREASE -> {
                getString(R.string.weight_increase, tvDiffWeight.text)
            }
            WEIGHT_DECREASE -> {
                getString(R.string.weight_decrease, tvDiffWeight.text)
            }
            else -> {
                getString(R.string.weight_not_change)
            }
        }
    }

    private fun saveDietHistory() {
        historyDiet!!.weightUntil = tvUntilWeight.text.toString().split(" ")[0].toFloat()
        historyDiet!!.weightAfter = tvAfterWeight.text.toString().split(" ")[0].toFloat()
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
        if (historyDiet!!.startTime != 0L){
            tvTime.text = resources.getQuantityString(R.plurals.days_plur, historyDiet!!.readablePeriod, historyDiet!!.readablePeriod)
        }else{
            tvTime.text = "-"
        }

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
        edtReview.setText(historyDiet!!.comment)

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
        if (isInteractive){
            setSymbolCounter()
        }
    }

    private fun setSymbolCounter() {
        tvSymbolCounter.visibility = View.VISIBLE
        edtReview.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvSymbolCounter.text = getString(R.string.history_symbol_counter, s!!.length.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
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
                weightDiffState = WEIGHT_DECREASE
            }
            historyDiet!!.weightUntil < historyDiet!!.weightAfter -> {
                tvDiffWeight.text = "${formater.format(historyDiet!!.weightAfter - historyDiet!!.weightUntil)} ${getString(R.string.kg_brok)}".replace(",", ".")
                tvDiffWeight.setTextColor(resources.getColor(R.color.increase_weight))
                lavStatus.pauseAnimation()
                lavStatus.setAnimation("history_diff_increase.json")
                lavStatus.rotation = 180.0f
                lavStatus.playAnimation()
                lavStatus.visibility = View.VISIBLE
                weightDiffState = WEIGHT_INCREASE
            }
            else -> {
                tvDiffWeight.text = "${formater.format(historyDiet!!.weightAfter - historyDiet!!.weightUntil)} ${getString(R.string.kg_brok)}".replace(",", ".")
                tvDiffWeight.setTextColor(resources.getColor(R.color.pause_weight))
                lavStatus.pauseAnimation()
                lavStatus.visibility = View.INVISIBLE
                weightDiffState = WEIGHT_NOT_CHANGE
            }
        }
    }

    private fun bindHeadAnim() {
        if (historyDiet?.state == Const.COMPLETED_DIET) {
            tvState.background = resources.getDrawable(R.drawable.shape_history_complete)
            tvState.text = resources.getString(R.string.completed_history)
            lavWin.visibility = View.VISIBLE
            if (isInteractive) {
                lavWin.playAnimation()
                startAnimFireworks()
            } else {
                lavWin.progress = 1.0f
            }
        } else {
            tvState.background = resources.getDrawable(R.drawable.shape_history_uncomplete)
            tvState.text = resources.getString(R.string.uncompleted_history)
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