package com.concordia.canary.ledger.weight_trends.domain.use_case

import kotlinx.coroutines.flow.Flow
import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.domain.repository.WeightTrendRepository

class LoadUserWeightsUseCase(private val weightTrendRepository: WeightTrendRepository) {
    suspend fun invoke(): Flow<Resource<List<TrendWeight>>> {
        return weightTrendRepository.loadAllActive()
    }
}