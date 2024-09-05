package com.concordia.canary.ledger.weight_trends.domain.repository

import kotlinx.coroutines.flow.Flow

import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

interface WeightTrendRepository {
    suspend fun loadAllActive(): Flow<Resource<List<TrendWeight>>>
}