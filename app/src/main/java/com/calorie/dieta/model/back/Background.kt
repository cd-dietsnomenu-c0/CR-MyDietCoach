package com.calorie.dieta.model.back

import java.io.Serializable

data class Background(var path : String, var urlPreview : String, var mode : Int, var speed : Float, var isUnlock : Boolean, var name : String) : Serializable
