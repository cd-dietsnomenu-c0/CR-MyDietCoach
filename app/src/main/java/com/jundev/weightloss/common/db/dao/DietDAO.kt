package com.jundev.weightloss.common.db.dao

import androidx.room.*
import com.jundev.weightloss.common.db.entities.DietPlanEntity

@Dao
interface DietDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dietPlanEntity: DietPlanEntity)

    @Query("select * from DietPlanEntity")
    fun getAll() : List<DietPlanEntity>

    @Delete
    fun clearDiet(dietPlanEntity: DietPlanEntity)

}