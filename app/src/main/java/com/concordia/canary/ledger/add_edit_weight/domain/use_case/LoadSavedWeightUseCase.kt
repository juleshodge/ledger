package com.concordia.canary.ledger.add_edit_weight.domain.use_case

import kotlinx.coroutines.flow.Flow
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository
import com.concordia.canary.ledger.util.Resource

class LoadSavedWeightUseCase(private val weightRepository: WeightRepository) {
    suspend operator fun invoke(weightId: Long): Flow<Resource<Weight>> {
        return weightRepository.getById(weightId)
    }
}