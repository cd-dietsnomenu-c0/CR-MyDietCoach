package com.meal.planner.common.db.dao

import androidx.room.*
import com.meal.planner.common.db.entities.DietPlanEntity
import com.meal.planner.common.db.entities.water.DrinksCapacities
import com.meal.planner.common.db.entities.FavoriteEntity
import com.meal.planner.common.db.entities.water.WaterIntake
import com.meal.planner.common.db.entities.water.WaterRate

@Dao
interface DietDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dietPlanEntity: DietPlanEntity)

    @Query("select * from DietPlanEntity")
    fun getAll(): List<DietPlanEntity>

    @Delete
    fun clearDiet(dietPlanEntity: DietPlanEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorite: FavoriteEntity)

    @Query("select * from FavoriteEntity where id = :index")
    fun getCurrentFavorite(index: Int): List<FavoriteEntity>

    @Query("select * from FavoriteEntity")
    fun getAllFavorites(): List<FavoriteEntity>

    @Query("delete from FavoriteEntity where id = :index")
    fun deleteFavorite(index: Int)

    @Insert
    fun addWater(intake: WaterIntake)

    @Query("select * from WaterIntake")
    fun getAllWaterIntakes(): List<WaterIntake>

    @Query("select * from WaterIntake where id >= :min and id <= :max")
    fun getCurrentWaterIntakes(min: Long, max: Long): List<WaterIntake>

    @Query("select * from DrinksCapacities where typeDrink = :typeDrink")
    fun getChoiceDrink(typeDrink: Int): List<DrinksCapacities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeDrink(drinksCapacities: DrinksCapacities)

    @Query("select * from DrinksCapacities")
    fun getAllCapacities(): List<DrinksCapacities>

    @Query("select * from DrinksCapacities where dirtyCapacity = (select max(dirtyCapacity) from DrinksCapacities)")
    fun getBiggestDrink(): List<DrinksCapacities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewRate(waterRate: WaterRate)

    @Query("select * from WaterRate")
    fun getAllWaterRates(): List<WaterRate>

}