package com.meal.planner.common.db.entities.water

import androidx.room.Entity
import androidx.room.PrimaryKey
// One intake of liquid (one press of add liquid on main screen)
@Entity
data class WaterIntake(@PrimaryKey var id : Long, var typeId : Int, var dirtyCapacity : Int, var clearCapacity : Int) {
}