package com.meal.planner.model.onboard

import java.io.Serializable

data class OnboardUI (var title : String, var text : String, var url : String, var isGradientTop : Boolean) : Serializable {
}