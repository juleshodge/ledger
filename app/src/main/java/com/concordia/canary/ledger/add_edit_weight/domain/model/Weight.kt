package com.concordia.canary.ledger.add_edit_weight.domain.model

import com.concordia.canary.ledger.add_edit_weight.data.local.entity.WeightEntity

data class Weight(
    val weightValue: Double,
    val units: InputUnits,
    val observationDate: Long,
    val extras: List<WeightExtras>
) {
    fun toEntity(): WeightEntity {

        val displayList = extras.joinToString()

        return WeightEntity(
            weightVal = weightValue,
            weightUnits = units.unitNumeric,
            obsDate = observationDate, weightExtras = displayList,
            createDate = System.currentTimeMillis()
        )
    }
}