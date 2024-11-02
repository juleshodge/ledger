package com.concordia.canary.ledger.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.concordia.canary.ledger.core.domain.model.TrendSettings

@Entity
data class TrendSettingsEntity(
    @PrimaryKey
    val settingId: Long?,
    val daysBack: Int,
    val modifiedDate: Long
) {
    fun toModel(selectedUnitsEntity: TrendSelectedUnitsEntity): TrendSettings {

        return TrendSettings(selectedUnitsEntity.toModel(), this.daysBack)
    }
}