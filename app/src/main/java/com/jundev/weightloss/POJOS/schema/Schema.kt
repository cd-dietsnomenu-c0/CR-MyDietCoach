package com.jundev.weightloss.POJOS.schema

import java.io.Serializable

data class Schema(var headImage : String, var title : String, var prop : String, var hardLevel : Int, var isHasTracker : Boolean, var isHasMenu : Boolean, var reverseDietIndexes : List<Int>, var isOld : Boolean, var countOldDiets : Int) :Serializable {
    constructor() : this("", "", "", 0, true, true, listOf(), false, -1)
}