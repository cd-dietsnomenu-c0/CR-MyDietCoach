package com.diets.weightloss.presentation.premium

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.diets.weightloss.App
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.utils.inapp.InAppCallback
import com.diets.weightloss.utils.inapp.SubscriptionProvider
import kotlinx.android.synthetic.main.premium_fragment.*
import java.text.DecimalFormat

class PremiumFragment : Fragment(R.layout.premium_fragment) {

    var oldYearPrice = ""
    var yearPrice = ""
    var monthPrice = ""

    var termsYear = ""
    var termsMonth = ""

    val where = Ampl.make_purchase_inside

    private var whichTwice = Ampl.twice_month

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var formatter = DecimalFormat("#.##")

        monthPrice = "${formatter.format(PreferenceProvider.monthPriceValue)} ${PreferenceProvider.premiumUnit}"
        yearPrice = "${formatter.format(PreferenceProvider.yearPriceValue)} ${PreferenceProvider.premiumUnit}"

        var oldYearPriceValue = PreferenceProvider.yearPriceValue!! + (PreferenceProvider.yearPriceValue!! * 0.2)


        oldYearPrice = "${formatter.format(oldYearPriceValue)} ${PreferenceProvider.premiumUnit}"

        termsMonth = getString(R.string.month_terms, monthPrice)
        termsYear = getString(R.string.year_terms, yearPrice)

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
    }

    fun handlInApp() {
        Ampl.makePurchaseTwice(where, whichTwice)
        PreferenceProvider.isHasPremium = true
        close()
    }

    fun close() {
        /*startActivity(Intent(MyApp.getInstance().applicationContext, MainActivity::class.java))
        activity!!.finishAffinity()*/
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

        tvPlayTerms.text = termsYear
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