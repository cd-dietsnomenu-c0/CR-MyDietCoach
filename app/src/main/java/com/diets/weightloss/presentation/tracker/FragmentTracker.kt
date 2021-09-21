package com.diets.weightloss.presentation.tracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.diets.weightloss.Config
import com.diets.weightloss.MainActivity
import com.diets.weightloss.model.interactive.Diet
import com.diets.weightloss.model.interactive.DietDay
import com.diets.weightloss.R
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.common.DBHolder
import com.diets.weightloss.common.GlobalHolder
import com.diets.weightloss.common.db.entities.DietPlanEntity
import com.diets.weightloss.presentation.diets.list.modern.article.DietAct
import com.diets.weightloss.presentation.tracker.alerts.*
import com.diets.weightloss.presentation.tracker.controller.DayAdapter
import com.diets.weightloss.presentation.tracker.controller.eats.EatAdapter
import com.diets.weightloss.presentation.tracker.controller.eats.IEat
import com.diets.weightloss.presentation.tracker.controller.lives.LiveAdapter
import com.diets.weightloss.presentation.tracker.controller.menu.IMenu
import com.diets.weightloss.presentation.tracker.controller.menu.MenuAdapter
import com.diets.weightloss.utils.ad.ActionAd
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*
import java.util.*

class FragmentTracker : Fragment(R.layout.fragment_tracker) {

    val CONGRATE_TAG = "CONGRATE_TAG"
    val LOSE_TAG = "LOSE_TAG"
    val EXIT_TAG = "EXIT_TAG"
    val CHEAT_TAG = "CHEAT_TAG"

    var currentDay = 0
    var dietState = 0
    var isDayCompleted = false
    var isDayViewBind = false
    var isCompleteAlertShowed = false

    lateinit var menuAdapter: MenuAdapter
    lateinit var liveAdapter: LiveAdapter
    lateinit var eatsAdapter: EatAdapter
    lateinit var daysAdapter: DayAdapter

    lateinit var exitAlert: DialogFragment
    lateinit var completeAlert: DialogFragment
    lateinit var loseFragment: DialogFragment
    lateinit var cheatMealAlert: DialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTopMargin()
        Ampl.openTracker()
        cvMainCard.setBackgroundResource(R.drawable.shape_card_tracker)

        rvEats.layoutManager = GridLayoutManager(activity, 2)
        rvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvDays.layoutManager = LinearLayoutManager(activity)
        rvLives.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        tvDetails.setOnClickListener {
            startActivity(Intent(activity, DietAct::class.java)
                    .putExtra(Config.NEW_DIET, getDiet())
                    .putExtra(Config.NEED_SHOW_CONNECT, false))
        }

        createAlerts()

        ivExit.setOnClickListener {
            exitAlert.show(activity!!.supportFragmentManager, EXIT_TAG)
        }

        tvTitleLives.setOnClickListener {
            showCheatAlert()
        }
    }


    private fun setTopMargin() {
        var height = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }

        var params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(0, height, 0, 0)
        nsvTracker.layoutParams = params
    }

    private fun showCheatAlert() {
        cheatMealAlert.show(activity!!.supportFragmentManager, CHEAT_TAG)
    }


    private fun createAlerts() {
        exitAlert = ExitAlert()
        exitAlert.setTargetFragment(this, 0)

        completeAlert = CongrateAlert()
        completeAlert.setTargetFragment(this, 0)

        loseFragment = LoseAlert()
        loseFragment.setTargetFragment(this, 0)

        cheatMealAlert = CheatMealAlert()
        cheatMealAlert.setTargetFragment(this, 0)
    }

    fun share() {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.tracker_share_1) + " " + getDiet()?.title + getString(R.string.tracker_share_2) + "\n"
                + "https://play.google.com/store/apps/details?id="
                + activity!!.packageName)
        startActivity(intent)
    }

    fun closeDiet() {
        DBHolder.delete()
        startActivity(Intent(activity, MainActivity::class.java))
        activity!!.finish()
    }

    fun restartDiet() {
        var entity = DietPlanEntity(getDiet()!!, DBHolder.get().difficulty, DBHolder.getTomorrowTimeTrigger())
        DBHolder.firstSet(entity, getDietDays()!!)
        startActivity(Intent(activity, LoadingActivity::class.java))
        activity!!.finish()
    }

    override fun onResume() {
        super.onResume()
        bindTracker()
    }


    private fun bindTracker() {
        if(DBHolder.getIfExist().name != DBHolder.NO_DIET_YET) {
            isCompleteAlertShowed = false
            isDayCompleted = false
            if (DBHolder.get().timeTrigger < Calendar.getInstance().timeInMillis) {
                dietState = DBHolder.bindNewDay(getDietDays()!!)
            }

            if (dietState == DBHolder.DIET_COMPLETED) {
                showCompletedAlert()
            } else if (dietState == DBHolder.DIET_LOSE) {
                showLosedAlert()
            }
            tvTrackerTitle.text = DBHolder.get().name
            currentDay = DBHolder.get().currentDay
            bindLives(DBHolder.get().difficulty, DBHolder.get().missingDays)
            bindEats(getDietDays()?.get(currentDay))
            bindMenu()
            bindDays()
            bindDayView()
        }
    }

    private fun showLosedAlert() {
        if(!loseFragment.isAdded) {
            loseFragment.show(activity!!.supportFragmentManager, LOSE_TAG)
        }
    }

    private fun showCompletedAlert() {
        if (!isCompleteAlertShowed && !completeAlert.isAdded) {
            completeAlert.show(activity!!.supportFragmentManager, CONGRATE_TAG)
            isCompleteAlertShowed = true
        }
    }


    private fun bindDays() {
        daysAdapter = DayAdapter(getDietDays()!!.size, DBHolder.get().numbersLosesDays, DBHolder.get().currentDay, isDayCompleted)
        rvDays.adapter = daysAdapter
    }


    private fun bindMenu() {
        tvCompleteMenu.visibility = View.INVISIBLE
        tvTitleMenu.visibility = View.VISIBLE

        tvBigDay.visibility = View.VISIBLE
        tvLabelDay.visibility = View.VISIBLE
        lavCompleteDay.visibility = View.INVISIBLE
        menuAdapter = MenuAdapter(getTypedArray(), object : IMenu {
            override fun completeDay() {
                closeDay()
            }
        })
        rvMenu.adapter = menuAdapter
    }

    private fun getTypedArray(): List<Int> {
        var list = mutableListOf<Int>()
        if (DBHolder.get().breakfastState == DBHolder.NOT_CHECKED) list.add(0)
        if (DBHolder.get().lunchState == DBHolder.NOT_CHECKED) list.add(1)
        if (DBHolder.get().dinnerState == DBHolder.NOT_CHECKED) list.add(2)
        if (DBHolder.get().snakeState == DBHolder.NOT_CHECKED) list.add(3)
        if (DBHolder.get().snake2State == DBHolder.NOT_CHECKED) list.add(4)
        return list
    }

    private fun bindDayView() {
        isDayViewBind = true
        tvBigDay.text = (DBHolder.get().currentDay + 1).toString()
        var progress = (DBHolder.get().currentDay + 1).toDouble() / getDietDays()!!.size!!.toDouble()
        cpbDay.progress = (progress * 100).toInt()
        if (isDayCompleted) {
            lavCompleteDay.visibility = View.VISIBLE
            lavCompleteDay.progress = 1.0f
        }
    }

    private fun bindEats(day: DietDay?) {
        eatsAdapter = EatAdapter(day!!.eats, DBHolder.get().breakfastState,
                DBHolder.get().lunchState, DBHolder.get().dinnerState,
                DBHolder.get().snakeState, DBHolder.get().snake2State, object : IEat {
            override fun checkEat(type: Int) {
                DBHolder.checkEat(type)
                refreshEats(type)
                ActionAd.action()
            }
        })
        rvEats.adapter = eatsAdapter
    }

    private fun bindLives(difficulty: Int, missingDays: Int) {
        liveAdapter = LiveAdapter(difficulty + 1, missingDays)
        rvLives.adapter = liveAdapter
    }

    private fun closeDay() {
        isDayCompleted = true
        tvTitleMenu.visibility = View.INVISIBLE
        tvLabelDay.visibility = View.INVISIBLE
        tvBigDay.visibility = View.INVISIBLE

        tvCompleteMenu.visibility = View.VISIBLE
        if (this::daysAdapter.isInitialized) {
            daysAdapter.notifyDayCompleted()
        }
        if (isDayViewBind) {
            lavCompleteDay.visibility = View.VISIBLE
            lavCompleteDay.playAnimation()
        }
        if (isDietCompleteNow()) {
            showCompletedAlert()
        }
    }

    private fun isDietCompleteNow(): Boolean {
        return DBHolder.get().currentDay + 1 == getDietDays()!!.size
                && DBHolder.get().missingDays <= DBHolder.get().difficulty
    }

    private fun refreshEats(type: Int) {
        menuAdapter.notify(type)
    }


    private fun getDietDays(): List<DietDay>? {
        for (diet in GlobalHolder.getGlobal().allDiets.dietList) {
            if (diet.title == DBHolder.get().name) {
                return diet.days
            }
        }
        return null
    }

    private fun getDiet(): Diet? {
        for (diet in GlobalHolder.getGlobal().allDiets.dietList) {
            if (diet.title == DBHolder.get().name) {
                return diet
            }
        }
        return null
    }

    fun rollBack() {
        AddLifeDialog().show(requireActivity().supportFragmentManager, "")
        dietState = DBHolder.DIET_CONTINUE
        DBHolder.rollBack()
        bindTracker()
    }

}