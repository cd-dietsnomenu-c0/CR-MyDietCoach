package com.calorie.dieta.model.water

data class WaterMarathon(val start: Long, val end: Long, val capacity: Int, var duration: Int = 0, var readableStart: String = "", var readableEnd: String = "", var readableCapacity: String = "") {
}