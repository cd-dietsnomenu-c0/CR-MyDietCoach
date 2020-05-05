package com.wsoteam.mydietcoach.tracker

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.mydietcoach.MainActivity
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.loading_activity.*

class LoadingActivity : AppCompatActivity(R.layout.loading_activity) {

    var lastNumber = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lavLoading.loop(false)
        lavLoadingComplete.loop(false)
        lavLoading.setAnimation("tracker_loading.json")
        lavLoading.speed = 0.8f
        lavLoadingComplete.setAnimation("tracker_loading_complete.json")
        lavLoading.playAnimation()

        lavLoading.addAnimatorUpdateListener {
            changeState(it.animatedValue as Float)
        }


        lavLoading.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                lavLoadingComplete.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })

        lavLoadingComplete.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startCountDown()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    private fun startCountDown() {
        object : CountDownTimer(500, 500){
            override fun onFinish() {
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun changeState(value: Float) {
        var progress = (value * 100).toInt()
        if (progress % 20 == 0 && progress != lastNumber){
            lastNumber = progress
            tvStateLoading.text = resources.getStringArray(R.array.loading_states)[progress / 20]
        }
    }

}