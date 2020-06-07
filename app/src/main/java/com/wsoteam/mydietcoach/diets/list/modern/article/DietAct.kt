package com.wsoteam.mydietcoach.diets.list.modern.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.*
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.ad.NativeSpeaker
import com.wsoteam.mydietcoach.analytics.Ampl
import com.wsoteam.mydietcoach.common.DBHolder
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity
import com.wsoteam.mydietcoach.diets.list.modern.article.dialogs.DifficultyFragment
import com.wsoteam.mydietcoach.diets.list.modern.article.dialogs.RewriteAlert
import com.wsoteam.mydietcoach.diets.list.modern.article.controller.DietAdapter
import com.wsoteam.mydietcoach.diets.list.modern.article.controller.IContents
import com.wsoteam.mydietcoach.diets.list.modern.article.controller.managers.LayoutManagerTopScroll
import com.wsoteam.mydietcoach.tracker.LoadingActivity
import kotlinx.android.synthetic.main.diet_act.*
import java.util.*

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet
    private var TAG = "ALERT_DIET_ACT"
    var isHided = false
    var isShowed = true
    var isNeedShowConnect = false
    lateinit var showAnim : Animation
    lateinit var hideAnim : Animation

    override fun onBackPressed() {
        AdWorker.showInter()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showAnim = AnimationUtils.loadAnimation(this, R.anim.show_start_button)
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.hide_start_button)

        AdWorker.checkLoad()
        diet = intent.getSerializableExtra(Config.NEW_DIET) as Diet
        isNeedShowConnect = intent.getSerializableExtra(Config.NEED_SHOW_CONNECT) as Boolean
        rvDiet.layoutManager = LayoutManagerTopScroll(this)
        var adapter = DietAdapter(diet, object : IContents {
            override fun moveTo(position: Int) {
                rvDiet.smoothScrollToPosition(position)
            }
        }, arrayListOf())
        rvDiet.adapter = adapter
        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<UnifiedNativeAd>) {
                adapter.insertAds(nativeList)
            }
        })
        if (isNeedShowConnect) {
            rvDiet.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 5) {
                        hideStartButton()
                    } else if (dy < -5){
                        showScrollButton()
                    }
                }
            })
        }else{
            flBottom.visibility = View.INVISIBLE
        }

        bindLike()
        Ampl.openNewDiet(diet.title)

        btnStart.setOnClickListener {
            if (DBHolder.get().name != DBHolder.NO_DIET_YET) {
                RewriteAlert().show(supportFragmentManager, TAG)
            }else{
                showNewDietAlert()
            }
        }
    }

    private fun bindLike() {
        lavLike.setMinFrame(45)
        lavLike.speed = 1.5f
        lavLike.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                lavLike.playAnimation()
            }
        })
    }

    private fun showScrollButton() {
        if (!isShowed){
            isHided = false
            isShowed = true
            flBottom.startAnimation(showAnim)
            flBottom.visibility = View.VISIBLE
        }
    }

    private fun hideStartButton() {
        if (!isHided){
            isShowed = false
            isHided = true
            flBottom.startAnimation(hideAnim)
            flBottom.visibility = View.INVISIBLE
        }
    }

    fun showNewDietAlert(){
        DifficultyFragment().show(supportFragmentManager, TAG)
    }

    fun startDietPlan(difficulty: Int) {
        Ampl.choiseHardLevel()
        var entity = DietPlanEntity(diet, difficulty, DBHolder.getTomorrowTimeTrigger())
        DBHolder.firstSet(entity, diet.days)
        startActivity(Intent(this, LoadingActivity::class.java))
        finishAffinity()
    }

}