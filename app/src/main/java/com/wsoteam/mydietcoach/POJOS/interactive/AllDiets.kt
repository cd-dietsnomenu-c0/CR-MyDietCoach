package com.wsoteam.mydietcoach.POJOS.interactive

import java.io.Serializable

data class AllDiets(var name : String, var dietList : List<Diet>) : Serializable {
}