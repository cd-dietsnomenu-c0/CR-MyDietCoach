package com.wsoteam.mydietcoach.POJOS.interactive

import java.io.Serializable

data class DietDay (var title: String, var image : String, var eats : List<Eat>)  : Serializable {
    constructor() : this("", "", listOf())
}