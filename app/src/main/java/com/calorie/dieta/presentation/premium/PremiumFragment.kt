package com.calorie.dieta.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.calorie.dieta.R
import com.calorie.dieta.SplashActivity
import com.calorie.dieta.presentation.premium.dialogs.ThankDialog
import com.calorie.dieta.utils.PreferenceProvider
import com.calorie.dieta.utils.analytics.Ampl
import com.calorie.dieta.utils.analytics.FBAnalytic
import com.calorie.dieta.utils.inapp.InAppCallback
import com.calorie.dieta.utils.inapp.SubscriptionProvider
import kotlinx.android.synthetic.main.premium_fragment.*
import java.text.DecimalFormat

class PremiumFragment : Fragment(R.layout.premium_fragment), ThankDialog.Callbacks {

    var oldYearPrice = ""
    var yearPrice = ""
    var monthPrice = ""

    var termsMonth = ""

    var where = Ampl.make_purchase_inside

    private var whichTwice = Ampl.twice_month

    private lateinit var thankDialog : ThankDialog
    val THANK_DIALOG_TAG = "THANK_DIALOG_TAG"

    override fun close() {
        restart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (activity is PremiumHostActivity){
            where = Ampl.make_purchase_start
        }

        var formatter = DecimalFormat("#.##")

        monthPrice = "${formatter.format(PreferenceProvider.monthPriceValue)} ${PreferenceProvider.premiumUnit}"
        yearPrice = "${formatter.format(PreferenceProvider.yearPriceValue)} ${PreferenceProvider.premiumUnit}"

        var oldYearPriceValue = PreferenceProvider.yearPriceValue!! + (PreferenceProvider.yearPriceValue!! * 0.2)


        oldYearPrice = "${formatter.format(oldYearPriceValue)} ${PreferenceProvider.premiumUnit}"

        termsMonth = getString(R.string.new_terms_month, monthPrice, yearPrice )

        selectMonth()
        flMonth.setOnClickListener {
            selectMonth()
        }

        flYear.setOnClickListener {
            selectYear()
        }

        tvMonthPrice.text = monthPrice
        tvYearPrice.text = yearPrice
        tvOldYearPrice.text = oldYearPrice

        tvMonthTerms.text = getString(R.string.description_month, monthPrice)
        tvYearTerms.text = getString(R.string.description_year, yearPrice)

        ivClose.setOnClickListener {

        }

        btnBuy.setOnClickListener {
            SubscriptionProvider.choiceSubNew(activity!!, getSubId(), object :
                    InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }
        prepareDialog()


    }

    fun handlInApp() {
        FBAnalytic.trial()
        Ampl.makePurchaseTwice(where, whichTwice)
        PreferenceProvider.isHasPremium = true
        thankDialog.show(requireActivity().supportFragmentManager, THANK_DIALOG_TAG)
    }

    private fun prepareDialog(){
        thankDialog = ThankDialog()
        thankDialog.setTargetFragment(this, 0)
    }

    private fun restart() {
        startActivity(Intent(requireActivity(), SplashActivity::class.java))
        requireActivity().finishAffinity()
    }

    private fun getSubId(): String {
        return if (ivBackMonth.isSelected) {
            IDS.WHITE_MONTH
        } else {
            IDS.WHITE_YEAR
        }
    }

    private fun selectYear() {
        ivBackMonth.isSelected = false
        ivBackYear.isSelected = true

        tvPlayTerms.text = termsMonth
        setYear()
    }

    private fun selectMonth() {
        ivBackMonth.isSelected = true
        ivBackYear.isSelected = false

        tvPlayTerms.text = termsMonth
        setMonth()
    }

    fun setMonth() {
        whichTwice = Ampl.twice_month
    }

    fun setYear() {
        whichTwice = Ampl.twice_year
    }
}