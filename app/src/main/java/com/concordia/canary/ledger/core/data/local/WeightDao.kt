package com.concordia.canary.ledger.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeightEntity)

    @Query("SELECT * FROM weightentity ORDER By obsDate desc")
    suspend fun getAll(): List<WeightEntity>

    @Query("SELECT * FROM weightentity WHERE id =:weightId")
    suspend fun getById(weightId: Long): WeightEntity

    @Query("UPDATE weightentity SET active=:status WHERE id =:weightId")
    suspend fun updateStatus(weightId: Long, status: Boolean)
}