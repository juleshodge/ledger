package com.concordia.canary.ledger.weight_trends.domain.model

import com.concordia.canary.ledger.core.domain.model.InputUnits

data class TrendStats(val average: Double, val averageUnits: InputUnits, val delta: Double) {
}