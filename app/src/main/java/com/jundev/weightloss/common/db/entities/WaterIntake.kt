package com.jundev.weightloss.common.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterIntake(@PrimaryKey var id : Long, var typeId : Int, var dirtyCapacity : Int, var clearCapacity : Int) {
}