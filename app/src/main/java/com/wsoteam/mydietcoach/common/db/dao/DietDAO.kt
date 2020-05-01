package com.wsoteam.mydietcoach.common.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity

@Dao
interface DietDAO {

    @Insert
    fun insert(dietPlanEntity: DietPlanEntity)

    @Query("select * from DietPlanEntity")
    fun getAll() : List<DietPlanEntity>

}