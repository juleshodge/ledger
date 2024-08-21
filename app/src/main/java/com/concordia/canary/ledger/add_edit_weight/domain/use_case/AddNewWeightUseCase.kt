package com.concordia.canary.ledger.add_edit_weight.domain.use_case

import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository

class AddNewWeightUseCase(private val weightRepository: WeightRepository) {
    suspend operator fun invoke(newWeight: Weight) {
        return weightRepository.add(newWeight)
    }
}