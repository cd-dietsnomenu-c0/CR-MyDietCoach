package com.calorie.dieta.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.calorie.dieta.common.db.dao.DietDAO
import com.calorie.dieta.common.db.dao.DietHistoryDAO
import com.calorie.dieta.common.db.entities.DietPlanEntity
import com.calorie.dieta.common.db.entities.FavoriteEntity
import com.calorie.dieta.common.db.entities.HistoryDiet
import com.calorie.dieta.common.db.entities.fix.BadMig
import com.calorie.dieta.common.db.entities.water.DrinksCapacities
import com.calorie.dieta.common.db.entities.water.WaterIntake
import com.calorie.dieta.common.db.entities.water.WaterRate

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class, WaterIntake::class, DrinksCapacities::class, WaterRate::class, BadMig::class, HistoryDiet::class], version = 5, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO
    abstract fun dietHistoryDAO(): DietHistoryDAO
}