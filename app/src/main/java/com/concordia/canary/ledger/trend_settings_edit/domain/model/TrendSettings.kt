package com.concordia.canary.ledger.trend_settings_edit.domain.model


data class TrendSettings(
    val preferredUnits: TrendSelectedUnits,
    val daysBack: Int = -1
)
