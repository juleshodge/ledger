package com.concordia.canary.ledger.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.core.domain.model.WeightExtras

@Entity
data class WeightEntity(
    @PrimaryKey
    val id: Long? = null,
    val weightVal: Double,
    val weightUnits: Int,
    val createDate: Long,
    val obsDate: Long,
    val weightExtras: String,
    val notes: String? = null
) {
    fun toWeightModel(): Weight {
        val unitFromNumeric = InputUnits.fromNumeric(weightUnits)
        return Weight(
            weightVal,
            unitFromNumeric,
            obsDate,
            WeightExtras.stringToList(weightExtras),
            notes
        )
    }
}