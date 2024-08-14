package com.concordia.canary.ledger.add_edit_weight.presentation.state

import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight

data class RecentWeightsState(
    val recentWeights: List<Weight> = emptyList(),
    val selectedRecentWeight: Weight? = null,
    val hasLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
