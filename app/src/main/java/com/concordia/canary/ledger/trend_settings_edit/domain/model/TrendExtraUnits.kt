package com.concordia.canary.ledger.trend_settings_edit.domain.model

enum class TrendExtraUnits(
    val displayName: String,
    val displayLongName: String,
    val unitNumeric: Int
) {
    StonesUnits("st", "Stones", 11),
    GramUnits("g", "gram", 12),
}