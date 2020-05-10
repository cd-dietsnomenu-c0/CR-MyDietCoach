package com.wsoteam.mydietcoach.common.db.dao

import androidx.room.*
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity

@Dao
interface DietDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dietPlanEntity: DietPlanEntity)

    @Query("select * from DietPlanEntity")
    fun getAll() : List<DietPlanEntity>

}