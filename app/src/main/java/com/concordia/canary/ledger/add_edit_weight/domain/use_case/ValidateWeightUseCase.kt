package com.concordia.canary.ledger.add_edit_weight.domain.use_case

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightValidationType

class ValidateWeightUseCase() {

    private fun parseValue(strVal: String): Double = try {
        strVal.toDouble();
    } catch (e: Exception) {
        0.0;
    }

    operator fun invoke(weight: String, units: InputUnits): WeightValidationType {

        val weightValue = parseValue(weight)

        if (weightValue < 0) {
            return WeightValidationType.WEIGHT_TOO_LOW
        }
        if (weightValue == 0.0) {
            return WeightValidationType.WEIGHT_INCOMPLETE
        }

        return WeightValidationType.VALID;
    }
}