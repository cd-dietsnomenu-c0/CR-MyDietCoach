package com.diets.weightloss.common.db.entities.water

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// Capacity certain type drink of all time of use (e.x. 100 l of juice and 95 l of coffee)
@Entity
open class DrinksCapacities(@PrimaryKey var typeDrink: Int,
                            var dirtyCapacity: Long,
                            @Ignore
                            var imgIndex: Int = -1,
                            @Ignore
                            var readableName: String = "",
                            @Ignore
                            var readableCapacity: String = "",
                            @Ignore
                            var globalPart: Float = 0f) {

    constructor() : this(0, 0L, -1, "", "", 0f)
}