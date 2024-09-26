package com.concordia.canary.ledger.trend_settings_edit.presentation.state

import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits

data class TrendSettingsState(
    val sel: TrendSelectedUnits = TrendSelectedUnits.NoneSelected,
    val daysBack: Int = -1
) {
}