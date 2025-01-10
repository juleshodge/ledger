package com.concordia.canary.ledger.core.util

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.util.WeightConverter
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

class TrendWeightConverter(private val simpleWeightConverter: WeightConverter) {
    fun transformWeight(
        baseWeight: TrendWeight,
        preferredUnits: TrendSelectedUnits
    ): TrendWeight {


        if (TrendSelectedUnits.isNoneSelectedOrOriginal(preferredUnits.numericType)) {
            return TrendWeight(
                baseWeight.id,
                baseWeight.originalValue,
                baseWeight.originalWeightUnits,
                baseWeight.originalWeightUnits,
                baseWeight.originalValue,
                baseWeight.observationDate,
                baseWeight.notes,
                baseWeight.weightExtras
            )
        }
        if (baseWeight.originalWeightUnits
                .unitNumeric == preferredUnits.subType
        ) {
            return TrendWeight(
                baseWeight.id,
                baseWeight.originalValue,
                baseWeight.originalWeightUnits,
                baseWeight.originalWeightUnits,
                baseWeight.originalValue,
                baseWeight.observationDate,
                baseWeight.notes,
                baseWeight.weightExtras
            )
        }

        val inputUnitsType = InputUnits.fromNumeric(preferredUnits.subType)

        val convertedVal =
            simpleWeightConverter.convert(
                baseWeight.originalValue,
                baseWeight.originalWeightUnits,
                inputUnitsType
            )

        return TrendWeight(
            baseWeight.id,
            baseWeight.originalValue,
            baseWeight.originalWeightUnits,
            inputUnitsType,
            convertedVal,
            baseWeight.observationDate,
            baseWeight.notes,
            baseWeight.weightExtras
        )
    }
}