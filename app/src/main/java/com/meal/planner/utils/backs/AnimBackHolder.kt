package com.meal.planner.utils.backs

import com.meal.planner.App
import com.meal.planner.R
import com.meal.planner.model.back.Background
import com.meal.planner.utils.PreferenceProvider

object AnimBackHolder {

    private var listBacks: ArrayList<Background>? = null

    private const val REVERSE_MODE_ON = 1

    private const val UNLOCK = 1
    private const val LOCK = 0

    fun getListBacks(): ArrayList<Background> {
        if (listBacks == null) {
            fillListBacks()
        }
        return listBacks!!
    }

    private fun fillListBacks() {
        var speeds = App.getInstance().resources.getIntArray(R.array.speed)
        var mode = App.getInstance().resources.getIntArray(R.array.reverse)
        var animPaths = App.getInstance().resources.getStringArray(R.array.back_animations)
        var urlsPreviews = App.getInstance().resources.getStringArray(R.array.preview_animations)
        var lockStates = stringToStates(PreferenceProvider.animUnlockBacksState, speeds.size)
        var names = App.getInstance().resources.getStringArray(R.array.backs_names)

        listBacks = arrayListOf()

        for (i in speeds.indices) {
            listBacks!!.add(Background(animPaths[i], urlsPreviews[i], mode[i], (speeds[i] * 0.01f), lockStates[i], names[i]))
        }
    }

    private fun stringToStates(animUnlockBacksState: String, size: Int): ArrayList<Boolean> {
        var arrayStates = arrayListOf<Boolean>()

        //fill def data
        for (i in 0 until size) {
            arrayStates.add(false)
        }

        var rawStates = animUnlockBacksState.split(PreferenceProvider.STATES_REGEX)

        for (i in rawStates) {
            arrayStates[i.toInt()] = true
        }

        return arrayStates
    }

    fun unlockItem(index: Int) {
        var states = PreferenceProvider.animUnlockBacksState
        states += "${PreferenceProvider.STATES_REGEX}$index"
        PreferenceProvider.animUnlockBacksState = states
    }
}