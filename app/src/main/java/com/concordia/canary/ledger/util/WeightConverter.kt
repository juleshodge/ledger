package com.concordia.canary.ledger.util

import com.concordia.canary.ledger.core.domain.model.InputUnits

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

    private fun convertToKg(lbValue: Double): Double {
        return lbValue * 0.453592
    }

    private fun convertToLbs(kgValue: Double): Double {
        return kgValue * 2.20462
    }
}
