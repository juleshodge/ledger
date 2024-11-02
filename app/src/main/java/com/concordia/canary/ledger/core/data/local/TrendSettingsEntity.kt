package com.concordia.canary.ledger.trend_settings_edit.data.local

import androidx.room.Entity
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSettings

@Entity
data class TrendSettingsEntity(
    val daysBack: Int,
    val modifiedDate: Long
) {
    fun toModel() {
        //TrendSelectedUnits.ConvertTrendUnit
        //TrendSettings(daysBack = this.daysBack, preferredUnits = )
    }
}