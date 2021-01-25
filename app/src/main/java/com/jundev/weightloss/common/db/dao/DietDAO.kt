package com.jundev.weightloss.common.db.dao

import androidx.room.*
import com.jundev.weightloss.common.db.entities.DietPlanEntity
import com.jundev.weightloss.common.db.entities.FavoriteEntity
import com.jundev.weightloss.common.db.entities.WaterIntake

@Dao
interface DietDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dietPlanEntity: DietPlanEntity)

    @Query("select * from DietPlanEntity")
    fun getAll() : List<DietPlanEntity>

    @Delete
    fun clearDiet(dietPlanEntity: DietPlanEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorite : FavoriteEntity)

    @Query("select * from FavoriteEntity where id = :index")
    fun getCurrentFavorite(index : Int) : List<FavoriteEntity>

    @Query("select * from FavoriteEntity")
    fun getAllFavorites() : List<FavoriteEntity>

    @Query("delete from FavoriteEntity where id = :index")
    fun deleteFavorite(index : Int)


    @Insert
    fun addWater(intake : WaterIntake)

    @Query("select * from WaterIntake")
    fun getAllWaterIntakes() : List<WaterIntake>
}