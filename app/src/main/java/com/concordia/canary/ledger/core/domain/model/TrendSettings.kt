package com.concordia.canary.ledger.trend_settings_edit.domain.model


data class TrendSettings(
    val preferredUnits: TrendSelectedUnits = TrendSelectedUnits.NoneSelected,
    val daysBack: Int = 365
)
