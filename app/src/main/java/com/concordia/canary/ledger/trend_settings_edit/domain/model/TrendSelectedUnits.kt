package com.concordia.canary.ledger.trend_settings_edit.domain.model

import com.concordia.canary.ledger.core.domain.model.InputUnits

sealed class TrendSelectedUnits(val subType: Int = 0) {
    data object NoneSelected : TrendSelectedUnits()
    class OriginalTrendUnit(val display: String) : TrendSelectedUnits()
    class ConvertTrendUnit(val targetUnits: InputUnits) :
        TrendSelectedUnits(subType = targetUnits.unitNumeric)
}