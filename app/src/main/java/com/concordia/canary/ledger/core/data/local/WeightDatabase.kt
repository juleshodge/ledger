package com.concordia.canary.ledger.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeightEntity::class], version = 3)
abstract class WeightDatabase : RoomDatabase() {
    abstract val dao: WeightDao
}