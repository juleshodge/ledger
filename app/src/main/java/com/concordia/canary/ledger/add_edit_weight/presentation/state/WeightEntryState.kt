package com.concordia.canary.ledger.add_edit_weight.presentation.state

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.util.UiText

data class WeightEntryState(
    val weightValue: String = "",
    val weightNotesValue: String = "",
    val weightObsTime: Long = 0,
    val weightUnits: InputUnits = InputUnits.KgUnits,
    val weightValueValid: Boolean = false,
    val weightValueError: UiText? = null,
    val availableUnits: List<InputUnits> = InputUnits.entries,
    val selectedExtras: List<WeightExtras> = emptyList()
)
