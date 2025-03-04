package com.concordia.canary.ledger.core.domain.model

sealed class TrendSelectedUnits(val numericType: Int, val subType: Int = 0) {
    data object NoneSelected : TrendSelectedUnits(1)
    class OriginalTrendUnit(val display: String) : TrendSelectedUnits(2)
    class ConvertTrendUnit(val targetUnits: InputUnits) :
        TrendSelectedUnits(subType = targetUnits.unitNumeric, numericType = 3)

    companion object {
        fun isNoneSelectedOrOriginal(numericType: Int): Boolean {
            return numericType <= 2
        }
    }
}