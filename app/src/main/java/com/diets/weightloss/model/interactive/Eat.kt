package com.diets.weightloss.model.interactive

import java.io.Serializable

 /*0 - breakfast
   1 - lunch
   2 - dinner
   4 - snack
   5 - second snack*/

data class Eat (var type : Int, var text : String)  : Serializable {
    constructor():this(0, "")
}