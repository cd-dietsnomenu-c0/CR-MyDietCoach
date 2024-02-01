package com.calorie.dieta.common.db.entities.water

import androidx.room.Entity
import androidx.room.PrimaryKey

// Norma of water on one day with timestamp
@Entity
data class WaterRate(@PrimaryKey var timestamp : Long, var rate : Int) {
}