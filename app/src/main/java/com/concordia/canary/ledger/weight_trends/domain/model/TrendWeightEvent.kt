package com.concordia.canary.ledger.weight_trends.domain.model


sealed class TrendWeightEvent() {
    data object NavToAdd : TrendWeightEvent()
    data object NavToTrendSettings : TrendWeightEvent()
    data class NavToEdit(val weightId: Long) : TrendWeightEvent()
}