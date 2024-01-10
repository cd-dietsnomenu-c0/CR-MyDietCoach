package com.meal.planner.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meal.planner.common.db.dao.DietDAO
import com.meal.planner.common.db.dao.DietHistoryDAO
import com.meal.planner.common.db.entities.DietPlanEntity
import com.meal.planner.common.db.entities.FavoriteEntity
import com.meal.planner.common.db.entities.HistoryDiet
import com.meal.planner.common.db.entities.fix.BadMig
import com.meal.planner.common.db.entities.water.DrinksCapacities
import com.meal.planner.common.db.entities.water.WaterIntake
import com.meal.planner.common.db.entities.water.WaterRate

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class, WaterIntake::class, DrinksCapacities::class, WaterRate::class, BadMig::class, HistoryDiet::class], version = 5, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO
    abstract fun dietHistoryDAO(): DietHistoryDAO
}