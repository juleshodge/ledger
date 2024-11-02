package com.concordia.canary.ledger.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.TrendSelectedUnits

@Entity
data class TrendSelectedUnitsEntity(
    @PrimaryKey
    val id: Long? = null,
    val numericType: Int,
    val subType: Int,
    val display: String,
    val modifiedData: Long
) {
    fun toModel(): TrendSelectedUnits {

        if (numericType == 1) {
            return TrendSelectedUnits.NoneSelected
        }
        if (numericType == 2) {
            return TrendSelectedUnits.OriginalTrendUnit(this.display)
        }

        val fromNumeric = InputUnits.fromNumeric(this.subType)
        return TrendSelectedUnits.ConvertTrendUnit(fromNumeric)
    }
}