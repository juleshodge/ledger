package com.concordia.canary.ledger.add_edit_weight.domain.use_case

import kotlinx.coroutines.flow.Flow
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository
import com.concordia.canary.ledger.util.Resource

class LoadRecentWeightsUseCase(private val weightRepository: WeightRepository) {
    suspend fun invoke(): Flow<Resource<List<Weight>>> {
        return weightRepository.loadAll()
    }
}