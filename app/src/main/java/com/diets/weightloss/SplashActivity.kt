package com.diets.weightloss

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.RenderMode
import com.amplitude.api.Amplitude
import com.diets.weightloss.POJOS.Global
import com.diets.weightloss.ad.AdWorker.init
import com.diets.weightloss.analytics.Ampl
import com.diets.weightloss.analytics.Ampl.Companion.openFromPush
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.common.FBWork.Companion.getFCMToken
import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.DietPlanEntity
import com.diets.weightloss.common.notifications.ScheduleSetter
import com.diets.weightloss.onboarding.OnboardActivity
import com.diets.weightloss.utils.ABConfig
import com.diets.weightloss.utils.LangChoicer
import com.diets.weightloss.utils.PrefWorker
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_activity.*
import java.util.*
import java.util.prefs.PreferenceChangeEvent


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    lateinit var scale: Animation
    lateinit var alpha: Animation
    lateinit var alphaText: Animation
    var goCounter = 0
    var maxGoCounter = 4
    var openFrom = ""
    var isFirstTime = false

    fun post() {
        goCounter += 1
        if (goCounter >= maxGoCounter) {
            var intent = Intent()
            if (PrefWorker.getVersion() != ABConfig.C && isFirstTime) {
                intent = Intent(this, OnboardActivity::class.java).putExtra(Config.PUSH_TAG, openFrom)
            } else {
                intent = Intent(this, MainActivity::class.java).putExtra(Config.PUSH_TAG, openFrom)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLocale()
        bindFCM()
        if (intent.extras != null && intent.extras!!.getString(Config.PUSH_TAG) != null && intent.extras!!.getString(Config.PUSH_TAG) == Config.OPEN_FROM_PUSH) {
            openFrom = Config.OPEN_FROM_PUSH
            openFromPush()
        }
        PrefWorker.setLastEnter(Calendar.getInstance().timeInMillis)
        bindTest()
        ScheduleSetter.setAlarm(this)
        loadAnimations()
        playAnim()
        loadData()
        setFirstTime()
    }

    private fun bindFCM() {
        var locale = PrefWorker.locale

        if (locale == PrefWorker.DEFAULT_LOCALE){
           locale = resources.configuration.locale.toString().split("_")[0]
        }

        when(locale){
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

        getFCMToken()
    }

    private fun bindLocale() {
        if (PrefWorker.locale != PrefWorker.DEFAULT_LOCALE) {
            val res: Resources = resources
            val dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
            conf.locale = Locale(PrefWorker.locale.toLowerCase())
            res.updateConfiguration(conf, dm)
        }
        Log.e("LOL", "${getResources().getConfiguration().locale}")
    }


    private fun bindTest() {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaults(R.xml.default_config)

        firebaseRemoteConfig.fetch(3600).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.activateFetched()
                Amplitude.getInstance().logEvent("norm_ab")
            } else {
                Amplitude.getInstance().logEvent("crash_ab")
            }
            setABTestConfig(firebaseRemoteConfig.getString(ABConfig.REQUEST_STRING))
        }
    }

    private fun setABTestConfig(version: String) {
        Log.e("LOL", version)
        PrefWorker.setVersion(version)
        Ampl.setABVersion(version)
        Ampl.setVersion()
        post()
    }

    private fun setFirstTime() {
        if (PrefWorker.getFirstTime() == "") {
            isFirstTime = true
            val calendar = Calendar.getInstance()
            var date = "${"%02d".format(calendar.get(Calendar.DAY_OF_MONTH))}.${"%02d".format(calendar.get(Calendar.MONTH) + 1)}.${calendar.get(Calendar.YEAR)}"
            PrefWorker.setFirstTime(date)
        }
    }

    private fun loadAnimations() {
        scale = AnimationUtils.loadAnimation(this, R.anim.scale_splash)
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_splash)
        alphaText = AnimationUtils.loadAnimation(this, R.anim.alpha_splash)

        alpha.fillAfter = true
        alpha.isFillEnabled = true
        scale.fillAfter = true
        scale.isFillEnabled = true

        scale.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ivLogo.startAnimation(alpha)
                tvText.startAnimation(alphaText)
                tvText.visibility = View.VISIBLE
                ivLogo.visibility = View.VISIBLE
                init(this@SplashActivity)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        alphaText.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                post()
                Log.e("LOL", "anim")
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }


    private fun playAnim() {
        lavLetter.setRenderMode(RenderMode.SOFTWARE)
        lavLetter.setAnimation("splash_letter.json")
        lavLetter.speed = 2.5f
        lavLetter.playAnimation()
        lavLetter.addAnimatorUpdateListener {
            if ((it.animatedValue as Float * 100).toInt() == 99) {
                lavLetter.startAnimation(scale)
            }
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


    companion object{
        private const val CHANGE_LANG_TAG = "CHANGE_LANG_TAG"

        fun getIntent(isChangedLang : Boolean, context: Context) : Intent{
            return Intent(context, SplashActivity::class.java).apply {
                putExtra(CHANGE_LANG_TAG, true)
            }
        }
    }
}