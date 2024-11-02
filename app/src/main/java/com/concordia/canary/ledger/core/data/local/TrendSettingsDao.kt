package com.concordia.canary.ledger.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TrendSettingsEntity)

    @Query("SELECT * FROM trendsettingsentity order by modifiedDate desc LIMIT 1")
    suspend fun get() : TrendSettingsEntity?
}