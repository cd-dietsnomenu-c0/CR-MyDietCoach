package com.jundev.weightloss.utils

import android.content.Context
import android.content.SharedPreferences
import com.jundev.weightloss.App

object PreferenceProvider {

    private const val RATE_MIND = "RATE_MIND"
    const val RATE_MIND_GOOD = "RATE_MIND_GOOD"
    const val RATE_MIND_BAD = "RATE_MIND_BAD"
    const val FIRST_TIME = "FIRST_TIME"
    const val USER_NAME = "USER_NAME"
    const val WALLPAPER_NUMBER = "WALLPAPER_NUMBER"
    const val COUNT_INTRO = "COUNT_INTRO"
    const val PHOTO_URI = "PHOTO_URI"
    const val EMPTY_PHOTO = "EMPTY_PHOTO"
    const val LAST_ENTER = "FIRST_ENTER"
    const val EMPTY_LAST_ENTER = -1L
    const val FIRST_REACT = "FIRST_REACT"
    const val SECOND_REACT = "SECOND_REACT"
    const val VERSION = "VERSION"

    private const val QUICK_PREFIX = "QUICK_PREFIX_"
    private const val CAPACITY_PREFIX = "CAPACITY_PREFIX"

    private const val SEX_TYPE = "SEX_TYPE"
    const val SEX_TYPE_MALE = 1
    const val SEX_TYPE_FEMALE = 0
    private const val WEIGHT = "WEIGHT"
    const val EMPTY = -1

    const val TRAINING_TAG = "TRAINING_TAG"
    const val HOT_TAG = "HOT_TAG"


    private fun getInstance(): SharedPreferences? {
        val sp = App.getInstance().getSharedPreferences(
                App.getInstance().packageName + ".SharedPreferences",
                Context.MODE_PRIVATE
        )
        return sp
    }

    private fun editor(put: (SharedPreferences.Editor?) -> SharedPreferences.Editor?) = put(getInstance()?.edit())?.apply()

    fun setRateMind(mind: String) = editor { it?.putString(RATE_MIND, mind) }
    fun getRateMind() = getInstance()?.getString(RATE_MIND, "")

    fun setFirstTime(time: String) = editor { it?.putString(FIRST_TIME, time) }
    fun getFirstTime() = getInstance()?.getString(FIRST_TIME, "")

    fun setName(name: String) = editor { it?.putString(USER_NAME, name) }
    fun getName() = getInstance()?.getString(USER_NAME, "")

    fun setBack(position: Int) = editor { it?.putInt(WALLPAPER_NUMBER, position) }
    fun getBack() = getInstance()?.getInt(WALLPAPER_NUMBER, 0)

    fun setCountIntro(count: Int) = editor { it?.putInt(COUNT_INTRO, count) }
    fun getCountIntro() = getInstance()?.getInt(COUNT_INTRO, 0)

    fun setPhoto(uri: String) = editor { it?.putString(PHOTO_URI, uri) }
    fun getPhoto() = getInstance()?.getString(PHOTO_URI, EMPTY_PHOTO)

    fun setLastEnter(time: Long) = editor { it?.putLong(LAST_ENTER, time) }
    fun getLastEnter() = getInstance()?.getLong(LAST_ENTER, EMPTY_LAST_ENTER)

    fun setFirstShow() = editor { it?.putBoolean(FIRST_REACT, true) }
    fun getFirstShow() = getInstance()?.getBoolean(FIRST_REACT, false)

    fun setSecondShow() = editor { it?.putBoolean(SECOND_REACT, true) }
    fun getSecondShow() = getInstance()?.getBoolean(SECOND_REACT, false)

    fun setVersion(version: String) = editor { it?.putString(VERSION, version) }
    fun getVersion() = getInstance()?.getString(VERSION, ABConfig.A)


    /*data -- id (number) from data array for img and hydratation count*/

    fun setQuickData(data: Int, index: Int) = editor {
        it?.putInt("$QUICK_PREFIX$index", data)
    }

    fun getQuickData(index: Int) = getInstance()?.getInt("$QUICK_PREFIX$index", -1)

    fun setCapacityIndex(capacityIndex: Int, index: Int) = editor {
        it?.putInt("$CAPACITY_PREFIX$index", capacityIndex)
    }

    fun getCapacityIndex(index: Int) = getInstance()?.getInt("$CAPACITY_PREFIX$index", -1)

    fun setWeight(weight: Int) = editor { it?.putInt(WEIGHT, weight) }
    fun getWeight() = getInstance()?.getInt(WEIGHT, EMPTY)

    fun setSex(type: Int) = editor { it?.putInt(SEX_TYPE, type) }
    fun getSex() = getInstance()?.getInt(SEX_TYPE, EMPTY)

    fun setTrainingFactor(isTurnOn: Boolean) = editor { it?.putBoolean(TRAINING_TAG, isTurnOn) }
    fun getTrainingFactor() = getInstance()?.getBoolean(TRAINING_TAG, true)

    fun setHotFactor(isTurnOn: Boolean) = editor { it?.putBoolean(HOT_TAG, isTurnOn) }
    fun getHotFactor() = getInstance()?.getBoolean(HOT_TAG, true)

}