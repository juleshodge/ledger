package com.concordia.canary.ledger.add_edit_weight.domain.model

import com.concordia.canary.ledger.core.data.local.WeightEntity
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras

data class Weight(
    val weightId: Long? = null,
    val weightValue: Double,
    val units: InputUnits,
    val observationDate: Long,
    val extras: List<WeightExtras>,
    val notes: String? = null,
    val active: Boolean,
) {
    fun toEntity(): WeightEntity {

        val displayList = extras.joinToString()

        return WeightEntity(
            id = this.weightId,
            weightVal = weightValue,
            weightUnits = units.unitNumeric,
            obsDate = observationDate,
            weightExtras = displayList,
            createDate = System.currentTimeMillis(),
            notes = notes,
            active = this.active
        )
    }
}