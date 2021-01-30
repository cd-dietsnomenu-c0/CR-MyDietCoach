package com.jundev.weightloss.common.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class DrinksCapacities(@PrimaryKey var typeDrink : Int, var dirtyCapacity : Long) {
}