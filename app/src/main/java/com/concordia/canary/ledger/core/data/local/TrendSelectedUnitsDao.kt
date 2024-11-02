package com.concordia.canary.ledger.trend_settings_edit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendSelectedUnitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(selectedUnits: TrendSelectedUnitsEntity)

    @Query("SELECT * FROM ")
    suspend fun get()
}