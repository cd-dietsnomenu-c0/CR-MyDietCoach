package com.diets.weightloss.common.db.entities.water

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterRate(@PrimaryKey var timestamp : Long, var rate : Int) {
}