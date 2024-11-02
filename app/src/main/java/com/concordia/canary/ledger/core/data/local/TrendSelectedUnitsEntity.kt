package com.concordia.canary.ledger.trend_settings_edit.data.local

data class TrendSelectedUnitsEntity(
    val numericType: Int,
    val subType: Int,
    val display: String,
    val modifiedData: Long
)