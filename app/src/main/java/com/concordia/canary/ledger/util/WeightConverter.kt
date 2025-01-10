package com.concordia.canary.ledger.util

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendExtraUnits

class WeightConverter {

    fun convert(
        rawValue: Double,
        sourceUnits: InputUnits,
        targetUnits: InputUnits
    ): Double {
        if (sourceUnits == targetUnits) {
            return rawValue
        }

        if (targetUnits == InputUnits.KgUnits) {
            return convertToKg(rawValue)
        }

        if (targetUnits == InputUnits.LbUnits) {
            return convertToLbs(rawValue)
        }

        throw IllegalArgumentException("Unsupported conversion")

    }

    fun convertToExtra(
        sourceUnits: InputUnits,
        targetUnits: TrendExtraUnits
    ) {

    }

    private fun convertToSt(lbValue: Double): Double {
        return lbValue / 14
    }

    private fun convertToKg(lbValue: Double): Double {
        return lbValue * 0.45359237
    }

    private fun convertToLbs(kgValue: Double): Double {
        return kgValue * 2.2046226218
    }
}
