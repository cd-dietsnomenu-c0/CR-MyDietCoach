package com.meal.planner.utils

import android.content.Context
import android.content.SharedPreferences
import com.meal.planner.App

object PreferenceProvider {

    const val EMPTY = -1

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


    private const val TRAINING_TAG = "TRAINING_TAG"
    private const val HOT_TAG = "HOT_TAG"
    private const val GLOBAL_WATER_COUNT_TAG = "GLOBAL_WATER_COUNT"

    private const val MANUAL_CHANGING_WATER_RATE = "MANUAL_CHANGING_WATER_RATE"


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
    fun getHotFactor() = getInstance()?.getBoolean(HOT_TAG, false)

    fun setGlobalWaterCount(diff: Int) = editor { it?.putInt(GLOBAL_WATER_COUNT_TAG, diff) }
    fun getGlobalWaterCount() = getInstance()?.getInt(GLOBAL_WATER_COUNT_TAG, 0)

    fun setWaterRateChangedManual(rate: Int) = editor { it?.putInt(MANUAL_CHANGING_WATER_RATE, rate) }
    fun getWaterRateChangedManual() = getInstance()?.getInt(MANUAL_CHANGING_WATER_RATE, EMPTY)

    private const val LANG_TAG = "LANG_TAG"
    const val DEFAULT_LOCALE = "DEFAULT_LOCALE"

    var locale: String
        get() = getInstance()?.getString(LANG_TAG, DEFAULT_LOCALE)!!
        set(value) = editor { it?.putString(LANG_TAG, value) }!!

    private const val LANG_WARNING_TAG = "LANG_WARNING_TAG"

    var isShowLangWarning: Boolean
        get() = getInstance()?.getBoolean(LANG_WARNING_TAG, false)!!
        set(value) = editor { it?.putBoolean(LANG_WARNING_TAG, value) }!!

    private const val IS_ON_WATER_SOUND = "IS_ON_WATER_SOUND"

    var isTurnOnWaterSound: Boolean
        get() = getInstance()?.getBoolean(IS_ON_WATER_SOUND, false)!!
        set(value) = editor { it?.putBoolean(IS_ON_WATER_SOUND, value) }!!


    //////////////////////////////////
    //////WATER NOTIFICATIONS/////////
    //////////////////////////////////


    private const val IS_ON_WATER_NOTIFICATIONS_TAG = "IS_ON_WATER_NOTIFICATIONS_TAG"

    var isTurnOnWaterNotifications: Boolean
        get() = getInstance()?.getBoolean(IS_ON_WATER_NOTIFICATIONS_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_ON_WATER_NOTIFICATIONS_TAG, value) }!!


    private const val AFTER_WATER_NORM_TAG = "AFTER_WATER_NORM_TAG"

    var isTurnOnAfterWaterNorm: Boolean
        get() = getInstance()?.getBoolean(AFTER_WATER_NORM_TAG, false)!!
        set(value) = editor { it?.putBoolean(AFTER_WATER_NORM_TAG, value) }!!

    private const val RECENTLY_WATER_TAG = "RECENTLY_WATER_TAG"

    var isTurnOnRecentlyWater: Boolean
        get() = getInstance()?.getBoolean(RECENTLY_WATER_TAG, true)!!
        set(value) = editor { it?.putBoolean(RECENTLY_WATER_TAG, value) }!!


    private const val START_NOTIF_TAG = "START_NOTIF_TAG"
    private const val DEFAULT_START_NOTIF_TIME = "09:00"

    var startWaterNotifTime: String
        get() = getInstance()?.getString(START_NOTIF_TAG, DEFAULT_START_NOTIF_TIME)!!
        set(value) = editor { it?.putString(START_NOTIF_TAG, value) }!!


    private const val END_NOTIF_TAG = "END_NOTIF_TAG"
    private const val DEFAULT_END_NOTIF_TIME = "22:00"

    var endWaterNotifTime: String
        get() = getInstance()?.getString(END_NOTIF_TAG, DEFAULT_END_NOTIF_TIME)!!
        set(value) = editor { it?.putString(END_NOTIF_TAG, value) }!!


    private const val TYPE_FREQUENT_TAG = "TYPE_FREQUENT_TAG"
    const val TYPE_30 = 0
    const val TYPE_60 = 1
    const val TYPE_90 = 2
    const val TYPE_120 = 3
    const val TYPE_150 = 4
    const val TYPE_180 = 5

    var frequentNotificationsType: Int
        get() = getInstance()?.getInt(TYPE_FREQUENT_TAG, TYPE_60)!!
        set(value) = editor { it?.putInt(TYPE_FREQUENT_TAG, value) }!!


    private const val TYPE_DAYS_TAG = "TYPE_DAYS_TAG"
    private const val DEFAULT_DAYS = "1-1-1-1-1-1-1" // String with delimiters, where every digit is day of week. 0 - not need notif, 1 - need notif

    var daysNotificationsType: String
        get() = getInstance()?.getString(TYPE_DAYS_TAG, DEFAULT_DAYS)!!
        set(value) = editor { it?.putString(TYPE_DAYS_TAG, value) }!!


    private const val LAST_WATER_NOTIF_TAG = "LAST_WATER_NOTIF_TAG"

    var lastTimeWaterNotif: Long
        get() = getInstance()?.getLong(LAST_WATER_NOTIF_TAG, 0L)!!
        set(value) = editor { it?.putLong(LAST_WATER_NOTIF_TAG, value) }!!


    private const val LAST_WATER_INTAKE_TAG = "LAST_WATER_INTAKE_TAG"

    var lastTimeWaterIntake: Long
        get() = getInstance()?.getLong(LAST_WATER_INTAKE_TAG, 0L)!!
        set(value) = editor { it?.putLong(LAST_WATER_INTAKE_TAG, value) }!!


    private const val LAST_NORM_WATER_DAY_TAG = "LAST_NORM_WATER_DAY_TAG"
    const val EMPTY_LAST_DAY = -1

    var lastNormWaterDay: Int
        get() = getInstance()?.getInt(LAST_NORM_WATER_DAY_TAG, EMPTY_LAST_DAY)!!
        set(value) = editor { it?.putInt(LAST_NORM_WATER_DAY_TAG, value) }!!


    private const val MONTH_PRICE_VALUE_TAG = "MONTH_PRICE_VALUE_TAG"
    private const val EMPTY_VALUE_MONTH = 0.99F
    var monthPriceValue: Float
        get() = getInstance()?.getFloat(MONTH_PRICE_VALUE_TAG, EMPTY_VALUE_MONTH)!!
        set(value) = editor { it?.putFloat(MONTH_PRICE_VALUE_TAG, value) }!!


    private const val YEAR_PRICE_VALUE_TAG = "YEAR_PRICE_VALUE_TAG"
    private const val EMPTY_VALUE_YEAR = 4.99F
    var yearPriceValue: Float
        get() = getInstance()?.getFloat(YEAR_PRICE_VALUE_TAG, EMPTY_VALUE_YEAR)!!
        set(value) = editor { it?.putFloat(YEAR_PRICE_VALUE_TAG, value) }!!


    private const val MONTH_PRICE_UNIT_TAG = "MONTH_PRICE_UNIT_TAG"
    private const val EMPTY_UNIT = "USD"
    var premiumUnit: String
        get() = getInstance()?.getString(MONTH_PRICE_UNIT_TAG, EMPTY_UNIT)!!
        set(value) = editor { it?.putString(MONTH_PRICE_UNIT_TAG, value) }!!


    private const val IS_HAS_PREMIUM_TAG = "IS_HAS_PREMIUM_TAG"
    var isHasPremium: Boolean
        get() = getInstance()?.getBoolean(IS_HAS_PREMIUM_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_HAS_PREMIUM_TAG, value) }!!

    private const val IS_SAW_PREMIUM = "IS_SAW_PREMIUM"
    var isSawPremium: Boolean
        get() = getInstance()?.getBoolean(IS_SAW_PREMIUM, false)!!
        set(value) = editor { it?.putBoolean(IS_SAW_PREMIUM, value) }!!


    private const val AD_PERCENT_TAG = "AD_PERCENT_TAG"
    var frequencyPercent: Int
        get() = getInstance()?.getInt(AD_PERCENT_TAG, 0)!!
        set(value) = editor { it?.putInt(AD_PERCENT_TAG, value) }!!


    private const val PREM_VER_TAG = "PREM_VER_TAG"
    var isNeedPrem: String
        get() = getInstance()?.getString(PREM_VER_TAG, "")!!
        set(value) = editor { it?.putString(PREM_VER_TAG, value) }!!

    private const val GRADE_VER_TAG = "GRADE_VER_TAG"
    var gradePremVer: String
        get() = getInstance()?.getString(GRADE_VER_TAG, ABConfig.GRADE_OLD)!!
        set(value) = editor { it?.putString(GRADE_VER_TAG, value) }!!

    private const val ACTION_AD_COUNTER_TAG = "ACTION_AD_COUNTER_TAG"
    var actionNumber: Int
        get() = getInstance()?.getInt(ACTION_AD_COUNTER_TAG, 0)!!
        set(value) = editor { it?.putInt(ACTION_AD_COUNTER_TAG, value) }!!

    private const val TYPE_HEAD_TAG = "TYPE_HEAD_TAG"
    const val ANIM_TYPE_HEAD = 1
    const val STATIC_TYPE_HEAD = 0
    var typeHead: Int
        get() = getInstance()?.getInt(TYPE_HEAD_TAG, STATIC_TYPE_HEAD)!!
        set(value) = editor { it?.putInt(TYPE_HEAD_TAG, value) }!!


    private const val ANIM_INDEX_TAG = "ANIM_INDEX_TAG"
    var animIndex: Int
        get() = getInstance()?.getInt(ANIM_INDEX_TAG, 0)!!
        set(value) = editor { it?.putInt(ANIM_INDEX_TAG, value) }!!

    private const val ANIM_BACK_STATE_TAG = "ANIM_BACK_STATE_TAG"
    const val DEF_BACK_STATE = "0.1.2"
    const val STATES_REGEX = "."

    var animUnlockBacksState: String
        get() = getInstance()?.getString(ANIM_BACK_STATE_TAG, DEF_BACK_STATE)!!
        set(value) = editor { it?.putString(ANIM_BACK_STATE_TAG, value) }!!


    private const val LOSE_DIET_TAG = "LOSE_DIET_TAG"
    var countLoseDiets: Int
        get() = getInstance()?.getInt(LOSE_DIET_TAG, 0)!!
        set(value) = editor { it?.putInt(LOSE_DIET_TAG, value) }!!

    private const val COMPLETE_DIET_TAG = "COMPLETE_DIET_TAG"
    var countCompleteDiets: Int
        get() = getInstance()?.getInt(COMPLETE_DIET_TAG, 0)!!
        set(value) = editor { it?.putInt(COMPLETE_DIET_TAG, value) }!!



}