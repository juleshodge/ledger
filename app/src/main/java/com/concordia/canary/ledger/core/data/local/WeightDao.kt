package com.concordia.canary.ledger.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeightEntity)

    @Query("SELECT * FROM weightentity")
    suspend fun getAll() : List<WeightEntity>
}