package com.wsoteam.mydietcoach.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsoteam.mydietcoach.common.db.dao.DietDAO
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity

@Database(entities = [DietPlanEntity::class], version = 1, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO
}