package com.meal.planner.model.interactive

import java.io.Serializable

data class AllDiets(var name : String, var dietList : List<Diet>) : Serializable {
    constructor() : this("",  listOf())
}