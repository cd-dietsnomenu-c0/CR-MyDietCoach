package com.diets.weightloss.common.db.dao

import androidx.room.*
import com.diets.weightloss.common.db.entities.HistoryDiet

@Dao
interface DietHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyDiet: HistoryDiet)

    @Query("select * from HistoryDiet")
    fun getAll(): List<HistoryDiet>

    @Delete
    fun clearDiet(historyDiet: HistoryDiet)
}