package com.concordia.canary.ledger.trend_settings_edit.domain.use_case


import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits

class LoadSelectableUnitsUseCase {
    operator fun invoke(): List<TrendSelectedUnits> {

        val availConversions: MutableList<TrendSelectedUnits> = InputUnits
            .entries
            .map { i -> TrendSelectedUnits.ConvertTrendUnit(i) }
            .toMutableList()

        availConversions.add(TrendSelectedUnits.OriginalTrendUnit("Keep Original blah"))

        return availConversions
    }
}