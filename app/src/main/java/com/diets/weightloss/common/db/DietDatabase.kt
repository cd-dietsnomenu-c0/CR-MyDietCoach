package com.diets.weightloss.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diets.weightloss.common.db.dao.DietDAO
import com.diets.weightloss.common.db.entities.DietPlanEntity
import com.diets.weightloss.common.db.entities.FavoriteEntity
import com.diets.weightloss.common.db.entities.fix.BadMig
import com.diets.weightloss.common.db.entities.water.DrinksCapacities
import com.diets.weightloss.common.db.entities.water.WaterIntake
import com.diets.weightloss.common.db.entities.water.WaterRate

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class, WaterIntake::class, DrinksCapacities::class, WaterRate::class, BadMig::class], version = 4, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO

}