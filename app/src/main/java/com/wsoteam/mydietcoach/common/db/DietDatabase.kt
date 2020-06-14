package com.wsoteam.mydietcoach.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.wsoteam.mydietcoach.common.db.dao.DietDAO
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity
import com.wsoteam.mydietcoach.common.db.entities.FavoriteEntity

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class], version = 2, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO

}