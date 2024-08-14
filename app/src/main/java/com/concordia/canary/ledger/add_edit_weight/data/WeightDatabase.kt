package com.concordia.canary.ledger.add_edit_weight.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.concordia.canary.ledger.add_edit_weight.data.local.WeightDao
import com.concordia.canary.ledger.add_edit_weight.data.local.entity.WeightEntity

@Database(entities = [WeightEntity::class], version = 1)
abstract class WeightDatabase : RoomDatabase() {
    abstract val dao: WeightDao
}