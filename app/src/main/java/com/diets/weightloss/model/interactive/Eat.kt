package com.diets.weightloss.model.interactive

import java.io.Serializable

data class Eat (var type : Int, var text : String)  : Serializable {
    constructor():this(0, "")
}