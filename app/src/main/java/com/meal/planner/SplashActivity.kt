package com.meal.planner

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.amplitude.api.Amplitude
import com.meal.planner.common.DBHolder
import com.meal.planner.common.FBWork.Companion.getFCMToken
import com.meal.planner.utils.LangChoicer
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.Moshi
import com.meal.planner.model.Global
import com.meal.planner.utils.analytics.Ampl
import com.meal.planner.common.GlobalHolder
import com.meal.planner.common.db.entities.DietPlanEntity
import com.meal.planner.presentation.premium.PremiumHostActivity
import com.meal.planner.utils.ABConfig
import com.meal.planner.utils.PreferenceProvider
import com.meal.planner.utils.ad.AdWorker.init
import com.meal.planner.utils.analytics.Ampl.Companion.openFromPush
import com.meal.planner.utils.inapp.SubscriptionProvider
import com.meal.planner.utils.notif.services.TopicWorker
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_activity.*
import java.util.*


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    var goCounter = 0
    var maxGoCounter = 4
    var openFrom = ""
    var isFirstTime = false

    fun post() {
        goCounter += 1
        if (goCounter >= maxGoCounter) {
            var intent = if (isFirstTime && PreferenceProvider.isNeedPrem == ABConfig.PREM_NEED && !PreferenceProvider.isHasPremium) {
                Intent(this, PremiumHostActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java).putExtra(Config.PUSH_TAG, openFrom)
            }
            Ampl.startAfterSplash()
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFakePrem()

        Ampl.runApp()
        bindLocale()
        bindFCM()
        if (intent.extras != null && intent.extras!!.getString(Config.PUSH_TAG) != null && intent.extras!!.getString(Config.PUSH_TAG) == Config.OPEN_FROM_PUSH) {
            openFrom = Config.OPEN_FROM_PUSH
            openFromPush()
        }
        bindTest()
        loadData()
        setFirstTime()
        SubscriptionProvider.startGettingPrice()
    }

    private fun setFakePrem() {
        PreferenceProvider.isHasPremium = true
    }


    override fun onPostResume() {
        super.onPostResume()
        runAnim()
    }

    private fun runAnim(){
        val dip = -72f
        val r: Resources = resources
        val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.displayMetrics
        )

        var show =  ValueAnimator.ofFloat(tvText.alpha, 1f)
        show.duration = 400L
        show.addUpdateListener {
            tvText.alpha = it.animatedValue as Float
            if (it.animatedFraction == 1.0f){
                init(this@SplashActivity)
                post()
            }
        }

        var moveToLeft = ValueAnimator.ofFloat(ivLogo.translationX, px)
        moveToLeft.duration = 600L
        moveToLeft.addUpdateListener {
            ivLogo.translationX = it.animatedValue as Float
            if (it.animatedFraction == 1.0f){
                //init(this@SplashActivity)
                show.start()
            }
        }
        moveToLeft.start()
    }

    private fun bindFCM() {
        var locale = PreferenceProvider.locale

        if (locale == PreferenceProvider.DEFAULT_LOCALE) {
            locale = resources.configuration.locale.toString().split("_")[0]
        }

        when (locale) {
            LangChoicer.RU -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_EN).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_RU).addOnSuccessListener { }
            }
            LangChoicer.EN -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_RU).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_EN).addOnSuccessListener { }
            }
            else -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_EN).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_RU).addOnSuccessListener { }
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(Config.EAT_TOPIC).addOnSuccessListener { }
        TopicWorker.changeWaterNotifState(PreferenceProvider.isTurnOnWaterNotifications)

        getFCMToken()
    }

    private fun bindLocale() {
        if (PreferenceProvider.locale != PreferenceProvider.DEFAULT_LOCALE) {
            val res: Resources = resources
            val dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
            conf.locale = Locale(PreferenceProvider.locale.toLowerCase())
            res.updateConfiguration(conf, dm)
        }
    }


    private fun bindTest() {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        var config = FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build()
        firebaseRemoteConfig.setConfigSettingsAsync(config)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_config)
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Amplitude.getInstance().logEvent("norm_ab")
            } else {
                Amplitude.getInstance().logEvent("crash_ab")
            }
            setABTestConfig(
                    firebaseRemoteConfig.getString(ABConfig.PREM_TAG), firebaseRemoteConfig.getString(ABConfig.GRADE_TAG)
            )
        }
    }

    private fun setABTestConfig(version: String, gradeVersion: String) {
        var defaultVer = ABConfig.PREM_NEED
        var defaultGradeVer = ABConfig.GRADE_OLD
        if (version != null && version != "") {
            defaultVer = version
        }
        if (gradeVersion != null && gradeVersion != "") {
            defaultGradeVer = gradeVersion
        }
        PreferenceProvider.isNeedPrem = defaultVer
        PreferenceProvider.gradePremVer = defaultGradeVer
        Ampl.setABVersion(version, defaultGradeVer)
        Ampl.setVersion()
        Log.e("LOL", defaultGradeVer)
        post()
    }

    private fun setFirstTime() {
        if (PreferenceProvider.getFirstTime() == "") {
            isFirstTime = true
            val calendar = Calendar.getInstance()
            var date = "${"%02d".format(calendar.get(Calendar.DAY_OF_MONTH))}.${"%02d".format(calendar.get(Calendar.MONTH) + 1)}.${calendar.get(Calendar.YEAR)}"
            PreferenceProvider.setFirstTime(date)
        }
    }



    private fun loadData() {
        loadDietData()
        loadDB()
    }

    private fun loadDB() {
        Single.fromCallable {
            var dietPlanEntity = App.getInstance().db.dietDAO().getAll()[0]
            dietPlanEntity
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> saveDiet(t) }) { _: Throwable -> saveEmptyDiet() }
    }

    private fun saveDiet(t: DietPlanEntity) {
        DBHolder.set(t)
        post()
        Log.e("LOL", "db")
    }

    private fun saveEmptyDiet() {
        DBHolder.setEmpty()
        post()
        Log.e("LOL", "empty db")
    }

    private fun loadDietData() {
        Single.fromCallable {
            val global: Global = getAsyncDietData()!!
            global
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: Global -> save(t) }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun save(t: Global) {
        GlobalHolder.setGlobal(t)
        post()
        Log.e("LOL", "aset")
    }

    private fun getAsyncDietData(): Global? {
        var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Global::class.java)
        try {
            var inputStream = assets.open(getString(R.string.adb_path))
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
        }
        return null
    }


    companion object {
        private const val CHANGE_LANG_TAG = "CHANGE_LANG_TAG"

        fun getIntent(isChangedLang: Boolean, context: Context): Intent {
            return Intent(context, SplashActivity::class.java).apply {
                putExtra(CHANGE_LANG_TAG, true)
            }
        }
    }
}