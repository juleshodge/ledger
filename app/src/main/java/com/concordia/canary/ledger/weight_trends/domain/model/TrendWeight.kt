package com.concordia.canary.ledger.weight_trends.domain.model


import com.concordia.canary.ledger.core.data.local.WeightEntity
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras

data class TrendWeight(
    val id: Long?,
    val originalValue: Double,
    val originalWeightUnits: InputUnits,
    val currentWeightUnits: InputUnits? = null,
    val currentWeightValue: Double? = null,
    val observationDate: Long,
    val notes: String? = null,
    val weightExtras: List<WeightExtras>
) {
    companion object {
        fun entityMapper(weightEntity: WeightEntity): TrendWeight {

            return TrendWeight(
                id = weightEntity.id,
                originalValue = weightEntity.weightVal,
                originalWeightUnits = InputUnits.fromNumeric(weightEntity.weightUnits),
                notes = weightEntity.notes,
                weightExtras = WeightExtras.stringToList(weightEntity.weightExtras),
                observationDate = weightEntity.obsDate
            )
        }
    }
}