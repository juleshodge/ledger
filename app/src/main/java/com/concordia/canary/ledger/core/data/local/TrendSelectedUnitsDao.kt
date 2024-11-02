package com.concordia.canary.ledger.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendSelectedUnitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(selectedUnits: TrendSelectedUnitsEntity)

    @Query("SELECT * FROM trendselectedunitsentity order by modifiedData desc LIMIT 1")
    suspend fun get() : TrendSelectedUnitsEntity
}