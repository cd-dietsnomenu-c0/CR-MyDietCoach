package com.jundev.weightloss.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jundev.weightloss.common.db.dao.DietDAO
import com.jundev.weightloss.common.db.entities.DietPlanEntity
import com.jundev.weightloss.common.db.entities.FavoriteEntity

@Database(entities = [DietPlanEntity::class, FavoriteEntity::class], version = 2, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDAO(): DietDAO

}