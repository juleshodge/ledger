package com.concordia.canary.ledger.weight_trends.presentation.state

import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

data class WeightTrendsState(
    val trendWeights: List<TrendWeight> = emptyList(),
    val isLoading: Boolean = false
)
