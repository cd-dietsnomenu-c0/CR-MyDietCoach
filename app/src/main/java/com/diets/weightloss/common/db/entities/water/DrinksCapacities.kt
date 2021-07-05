package com.diets.weightloss.common.db.entities.water

import androidx.room.Entity
import androidx.room.PrimaryKey

// Capacity certain type drink of all time of use (e.x. 100 l of juice and 95 l of coffee)
@Entity
open class DrinksCapacities(@PrimaryKey var typeDrink : Int, var dirtyCapacity : Long, var imgIndex : Int = -1, var readableName : String = "", var readableCapacity : String = "", var globalPart : Float = 0f) {
}