package com.concordia.canary.ledger.add_edit_weight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras


@Entity
data class WeightEntity(
    @PrimaryKey
    val id: Long? = null,
    val weightVal: Double,
    val weightUnits: Int,
    val createDate: Long,
    val obsDate: Long,
    val weightExtras: String
) {
    fun toWeightModel(): Weight {
        val weightUnit = InputUnits.entries.first { u -> u.unitNumeric == weightUnits }
        return Weight(weightVal, weightUnit, obsDate, stringToList())
    }

    private fun stringToList(): List<WeightExtras> {
        if (weightExtras.isBlank()) {
            return emptyList()
        }
        val split: List<String> = weightExtras.split(",")

        val matching = split.map { str -> WeightExtras.entries.first { it -> it.name == str.trim() } }

        return matching;
    }
}