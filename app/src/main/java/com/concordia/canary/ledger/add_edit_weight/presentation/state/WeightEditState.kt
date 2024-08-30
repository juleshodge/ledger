package com.concordia.canary.ledger.add_edit_weight.presentation.state

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.util.UiText

data class WeightEditState(
    val changeMade: Boolean = false,
    val loadFailed: Boolean = false,
    val loading: Boolean = false,
    val weightValue: String = "0.0",
    val weightNotesValue: String? = null,
    val weightId: Long? = null,
    val weightObsTime: Long = 0,
    val weightUnits: InputUnits = InputUnits.KgUnits,
    val weightValueValid: Boolean = false,
    val weightValueError: UiText? = null,
    val availableUnits: List<InputUnits> = InputUnits.entries,
    val selectedExtras: List<WeightExtras> = emptyList()
)
