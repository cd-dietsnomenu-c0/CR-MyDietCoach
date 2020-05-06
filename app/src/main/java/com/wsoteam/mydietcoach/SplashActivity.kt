package com.wsoteam.mydietcoach

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.RenderMode
import com.squareup.moshi.Moshi
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.common.DBHolder
import com.wsoteam.mydietcoach.common.GlobalHolder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    lateinit var scale : Animation
    lateinit var alpha : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAnimations()
        playAnim()
        loadData()
    }

    private fun loadAnimations(){
        scale = AnimationUtils.loadAnimation(this, R.anim.scale_splash)
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_splash)

        alpha.fillAfter = true
        alpha.isFillEnabled = true
        scale.fillAfter = true
        scale.isFillEnabled = true

        scale.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ivLogo.startAnimation(alpha)
                tvText.startAnimation(alpha)
                tvText.visibility = View.VISIBLE
                ivLogo.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }


    private fun playAnim() {
        lavLetter.setRenderMode(RenderMode.SOFTWARE)
        lavLetter.setAnimation("splash_letter.json")
        lavLetter.speed = 1.9f
        lavLetter.playAnimation()
        lavLetter.addAnimatorUpdateListener {
            if ((it.animatedValue as Float * 100).toInt() == 99){
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
                .subscribe({ t -> DBHolder.set(t) }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun loadDietData() {
        Single.fromCallable {
            val global: Global = getAsyncDietData()!!
            global
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: Global -> saveAndGo(t) }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun saveAndGo(t: Global) {
        GlobalHolder.setGlobal(t)
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getAsyncDietData(): Global? {
        var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Global::class.java)
        try {
            var inputStream = assets.open("adb.json")
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
}