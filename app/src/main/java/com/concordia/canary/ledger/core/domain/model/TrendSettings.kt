package com.concordia.canary.ledger.core.domain.model


data class TrendSettings(
    val preferredUnits: TrendSelectedUnits = TrendSelectedUnits.NoneSelected,
    val daysBack: Int = 365
)
