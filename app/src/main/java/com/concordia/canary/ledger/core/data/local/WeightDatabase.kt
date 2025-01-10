package com.concordia.canary.ledger.core.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeightEntity::class,
        TrendSelectedUnitsEntity::class, TrendSettingsEntity::class],
    version = 6,
    exportSchema = true,
)
abstract class WeightDatabase : RoomDatabase() {
    abstract val dao: WeightDao

    abstract val selectedUnitsDao: TrendSelectedUnitsDao

    abstract val trendSettingsDao: TrendSettingsDao
}