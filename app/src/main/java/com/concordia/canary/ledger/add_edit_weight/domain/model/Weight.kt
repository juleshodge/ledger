package com.concordia.canary.ledger.add_edit_weight.domain.model

import com.concordia.canary.ledger.core.data.local.WeightEntity
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras

data class Weight(
    val weightValue: Double,
    val units: InputUnits,
    val observationDate: Long,
    val extras: List<WeightExtras>,
    val notes: String? = null,
) {
    fun toEntity(): WeightEntity {

        val displayList = extras.joinToString()

        return WeightEntity(
            weightVal = weightValue,
            weightUnits = units.unitNumeric,
            obsDate = observationDate,
            weightExtras = displayList,
            createDate = System.currentTimeMillis(),
            notes = notes
        )
    }
}