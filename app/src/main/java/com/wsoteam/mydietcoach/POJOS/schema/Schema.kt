package com.wsoteam.mydietcoach.POJOS.schema

data class Schema(var headImage : String, var title : String, var hardLevel : Int, var isHasTracker : Boolean, var isHasMenu : Boolean, var reverseDietIndexes : List<Int>, var isOld : Boolean, var countOldDiets : Int) {
    constructor() : this("", "", 0, true, true, listOf(), false, -1)
}