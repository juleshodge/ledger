package com.concordia.canary.ledger.weight_trends.domain.model


sealed class TrendWeightEvent() {
    data object NavToAdd : TrendWeightEvent()
    data class NavToEdit(val weightId: Long) : TrendWeightEvent()
}