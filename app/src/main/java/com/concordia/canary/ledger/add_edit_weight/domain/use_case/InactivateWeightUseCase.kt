package com.concordia.canary.ledger.add_edit_weight.domain.use_case

import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository

class InactivateWeightUseCase(private val weightRepository: WeightRepository) {
    suspend fun invoke(id: Long, newVal: Boolean) {
        return weightRepository.updatedActive(id, newVal)
    }
}