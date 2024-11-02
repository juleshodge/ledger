package com.concordia.canary.ledger.trend_settings_edit.domain.model

import com.concordia.canary.ledger.core.domain.model.InputUnits

sealed class TrendSelectedUnits(val numericType: Int, val subType: Int = 0) {
    data object NoneSelected : TrendSelectedUnits(1)
    class OriginalTrendUnit(val display: String) : TrendSelectedUnits(2)
    class ConvertTrendUnit(val targetUnits: InputUnits) :
        TrendSelectedUnits(subType = targetUnits.unitNumeric, numericType = 3)
    fun fromNumeric(v : Int) : TrendSelectedUnits {
       return NoneSelected
    }
}