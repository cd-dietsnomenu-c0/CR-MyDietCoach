package com.diets.weightloss.common.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.diets.weightloss.common.db.entities.HistoryDiet

@Dao
interface DietHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyDiet: HistoryDiet)

    @Query("select * from HistoryDiet")
    fun getAll(): LiveData<List<HistoryDiet>>

    @Delete
    fun clearDiet(historyDiet: HistoryDiet)
}