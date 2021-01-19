package com.jundev.weightloss.presentation.water

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jundev.weightloss.R
import com.jundev.weightloss.model.water.QuickWaterList
import com.jundev.weightloss.utils.PreferenceProvider
import com.jundev.weightloss.presentation.water.controller.DrinkAdapter
import com.jundev.weightloss.presentation.water.controller.IDrinkAdapter
import com.jundev.weightloss.presentation.water.controller.capacities.CapacityAdapter
import com.jundev.weightloss.presentation.water.controller.capacities.ICapacityAdapter
import com.jundev.weightloss.presentation.water.controller.quick.IQuick
import com.jundev.weightloss.presentation.water.controller.quick.QuickAdapter
import com.jundev.weightloss.presentation.water.toasts.FillMeasToast
import kotlinx.android.synthetic.main.bottom_begin_meas.*
import kotlinx.android.synthetic.main.bottom_water_settings.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*
import kotlin.math.roundToInt

class FragmentWaterTracker : Fragment(R.layout.fragment_water_tracker) {

    var bsWaterSettings: BottomSheetBehavior<LinearLayout>? = null
    var bsBeginMeas: BottomSheetBehavior<LinearLayout>? = null

    var drinkTypeAdapter: DrinkAdapter? = null
    var capacityAdapter: CapacityAdapter? = null
    var quickAdapter: QuickAdapter? = null

    var progress = 0.0f
    var lastChangeQuickItem = -1

    var sexType = PreferenceProvider.SEX_TYPE_FEMALE

    lateinit var vm: WaterVM

    var mediumFrame = 24
    var startFrame = 0
    var endFrame = 60

    var isNeedAnimateDailyRate = false
    var isNeedAnimateCapacity = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bsWaterSettings = BottomSheetBehavior.from(llBSWatersettings)

        rvQuickDrink.layoutManager = GridLayoutManager(activity, 2)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvCapacities.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        vm = ViewModelProviders.of(this).get(WaterVM::class.java)

        fillWaterSettingsBS()
        setExtraViews()

        if (PreferenceProvider.getWeight() == PreferenceProvider.EMPTY) {
            showBeginMeasBS()
        }

        bsWaterSettings!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivDimBackground.visibility = View.GONE
                }
            }
        })


    }


    private fun setExtraViews() {
        if (PreferenceProvider.getTrainingFactor()!!) {
            setTrainingStateOn()
        } else {
            setTrainingStateOff()
        }

        if (PreferenceProvider.getHotFactor()!!) {
            setHotStateOn()
        } else {
            setHotStateOff()
        }
    }

    private fun setHotStateOn() {
        lavHot.setMinAndMaxFrame(mediumFrame, endFrame)
        lavHot.frame = mediumFrame
        cvHot.setOnClickListener {
            vm.changeHotFactor(false)
            lavHot.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    lavHot.removeAllAnimatorListeners()
                    setHotStateOff()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            lavHot.playAnimation()
        }
    }

    private fun setHotStateOff() {
        lavHot.setMinAndMaxFrame(startFrame, mediumFrame)
        lavHot.frame = startFrame
        cvHot.setOnClickListener {
            vm.changeHotFactor(true)
            lavHot.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    lavHot.removeAllAnimatorListeners()
                    setHotStateOn()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            lavHot.playAnimation()
        }
    }

    private fun setTrainingStateOn() {
        lavTraining.setMinAndMaxFrame(mediumFrame, endFrame)
        lavTraining.frame = mediumFrame
        cvTraining.setOnClickListener {
            vm.changeTrainingFactor(false)
            lavTraining.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    lavTraining.removeAllAnimatorListeners()
                    setTrainingStateOff()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            lavTraining.playAnimation()
        }
    }

    private fun setTrainingStateOff() {
        lavTraining.setMinAndMaxFrame(startFrame, mediumFrame)
        lavTraining.frame = startFrame
        cvTraining.setOnClickListener {
            vm.changeTrainingFactor(true)
            lavTraining.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    lavTraining.removeAllAnimatorListeners()
                    setTrainingStateOn()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            lavTraining.playAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getQuickLD().observe(this,
                Observer {
                    fillQuick(it)
                })
        vm.getDailyRate().observe(this,
                Observer {
                    if (isNeedAnimateDailyRate) {
                        changeDailyRate(it)
                    } else {
                        tvRate.text = "$it"
                        setPercent()
                        isNeedAnimateDailyRate = true
                    }
                })
        vm.getCurrentCapacity().observe(this, Observer {
            if (isNeedAnimateCapacity) {
                changeCurrentCapacity(it)
            } else {
                tvCapacity.text = "$it"
                setPercent()
                isNeedAnimateCapacity = true
            }
        })
    }

    private fun changeCurrentCapacity(it: Int) {
        var oldValue = tvCapacity.text.toString().toInt()
        var animator = ValueAnimator.ofInt(oldValue, it)
        changePercentAnim(it, tvRate.text.toString().toInt())
        animator.addUpdateListener {
            tvCapacity.text = "${it.animatedValue}"
        }
        animator.start()
    }


    private fun changeDailyRate(it: Int) {
        var oldValue = tvRate.text.toString().toInt()
        var animator = ValueAnimator.ofInt(oldValue, it)
        changePercentAnim(tvCapacity.text.toString().toInt(), it)
        animator.addUpdateListener {
            tvRate.text = "${it.animatedValue}"
        }
        animator.start()
    }


    private fun setPercent() {
        var percentValue = (tvCapacity.text.toString().toFloat() / tvRate.text.toString().toFloat() * 100f).roundToInt()
        tvPercent.text = "$percentValue%"
    }

    private fun changePercentAnim(capacity : Int, dailyRate : Int) {
        var oldValue = tvPercent.text.toString().split("%")[0].toInt()
        var newValue = (capacity.toFloat() / dailyRate.toFloat() * 100f).roundToInt()

        if (newValue > 100) {
            newValue = 100
        }

        var animator = ValueAnimator.ofInt(oldValue, newValue)
        animator.addUpdateListener {
            tvPercent.text = "${it.animatedValue}%"
        }
        animator.start()
    }


    private fun showBeginMeasBS() {
        ivDimBackground.visibility = View.VISIBLE
        bsBeginMeas = BottomSheetBehavior.from(llBSBeginMeas)
        bsBeginMeas!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivDimBackground.visibility = View.GONE
                } else if (p1 == BottomSheetBehavior.STATE_DRAGGING) {
                    bsBeginMeas!!.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })
        bsBeginMeas!!.state = BottomSheetBehavior.STATE_EXPANDED

        flFemale.setOnClickListener {
            lavCircleFemale.frame = 0
            lavTickFemale.frame = 0
            lavCircleFemale.visibility = View.VISIBLE
            lavCircleFemale.playAnimation()
            lavTickFemale.visibility = View.VISIBLE
            lavTickFemale.playAnimation()

            lavTickMale.visibility = View.INVISIBLE
            lavCircleMale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_FEMALE
        }

        flMale.setOnClickListener {
            lavCircleMale.frame = 0
            lavTickMale.frame = 0
            lavCircleMale.visibility = View.VISIBLE
            lavCircleMale.playAnimation()
            lavTickMale.visibility = View.VISIBLE
            lavTickMale.playAnimation()

            lavTickFemale.visibility = View.INVISIBLE
            lavCircleFemale.visibility = View.INVISIBLE

            sexType = PreferenceProvider.SEX_TYPE_MALE
        }

        btnContinue.setOnClickListener {
            var weight = 50
            if (edtWeight.text.toString() != "" && edtWeight.text.toString() != " ") {
                weight = edtWeight.text.toString().toInt()
                if (weight in 21..199) {
                    PreferenceProvider.setWeight(weight)
                    PreferenceProvider.setSex(sexType)
                    bsBeginMeas!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    FillMeasToast.show(activity!!)
                } else {
                    Toast.makeText(activity, getString(R.string.input_error_not_in_limit), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, getString(R.string.input_error_empty), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fillQuick(it: QuickWaterList) {
        if (quickAdapter == null) {
            quickAdapter = QuickAdapter(object : IQuick {
                override fun onAdd(position: Int) {
                    vm.addWater(position)
                }

                override fun onSettings(position: Int) {
                    ivDimBackground.visibility = View.VISIBLE
                    prepareWaterSettingsBS(position, PreferenceProvider.getQuickData(position)!!, PreferenceProvider.getCapacityIndex(position)!!)
                    bsWaterSettings!!.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }, it)
            rvQuickDrink.adapter = quickAdapter
        } else {
            quickAdapter!!.setNewData(it, lastChangeQuickItem)
        }
    }

    private fun prepareWaterSettingsBS(position: Int, quickData: Int, capacityIndex: Int) {
        drinkTypeAdapter!!.selectNew(quickData)
        capacityAdapter!!.selectNew(capacityIndex)
        rvDrinks.scrollToPosition(quickData)
        rvCapacities.scrollToPosition(capacityIndex)

        btnSaveQuick.setOnClickListener(null)
        btnSaveQuick.setOnClickListener {
            lastChangeQuickItem = position
            bsWaterSettings!!.state = BottomSheetBehavior.STATE_COLLAPSED
            vm.saveNewQuickItem(capacityAdapter!!.getSelectedNumber(), drinkTypeAdapter!!.getSelectedNumber(), position)
        }
    }


    private fun fillWaterSettingsBS() {
        drinkTypeAdapter = DrinkAdapter(resources.getStringArray(R.array.water_drinks_names), 1, object : IDrinkAdapter {
            override fun select(newSelect: Int, oldSelect: Int) {
                drinkTypeAdapter!!.unSelect(oldSelect)
            }
        })
        rvDrinks.adapter = drinkTypeAdapter


        capacityAdapter = CapacityAdapter(resources.getStringArray(R.array.drink_capacity_values),
                convertImgsIds(resources.getIntArray(R.array.drink_capacity_icons)), object : ICapacityAdapter {
            override fun select(newSelect: Int, oldSelect: Int) {
                capacityAdapter!!.unSelect(oldSelect)
            }
        }, 1)
        rvCapacities.adapter = capacityAdapter

    }

    private fun convertImgsIds(imgsIds: IntArray): ArrayList<Int> {
        var convertedImgsIds: ArrayList<Int> = arrayListOf()
        for (j in imgsIds.indices) {
            convertedImgsIds.add(resources.obtainTypedArray(R.array.drink_capacity_icons).getResourceId(j, -1))
        }
        return convertedImgsIds
    }
}