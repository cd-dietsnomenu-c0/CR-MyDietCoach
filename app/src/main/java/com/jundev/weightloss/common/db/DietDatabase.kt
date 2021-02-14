package com.jundev.weightloss.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jundev.weightloss.common.db.dao.DietDAO
import com.jundev.weightloss.common.db.entities.DietPlanEntity
import com.jundev.weightloss.common.db.entities.water.DrinksCapacities
import com.jundev.weightloss.common.db.entities.FavoriteEntity
import com.jundev.weightloss.common.db.entities.water.WaterIntake
import com.jundev.weightloss.common.db.entities.water.WaterRate

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class, WaterIntake::class, DrinksCapacities::class, WaterRate::class], version = 3, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO

}