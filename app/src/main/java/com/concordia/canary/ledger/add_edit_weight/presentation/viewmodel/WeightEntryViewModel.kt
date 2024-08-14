package com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel

import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightValidationType
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.AddNewWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.ValidateWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.presentation.state.WeightEntryState
import com.concordia.canary.ledger.util.UiText

@HiltViewModel
class WeightEntryViewModel @Inject constructor(
    val validateWeightUseCase: ValidateWeightUseCase,
    val addNewWeightUseCase: AddNewWeightUseCase
) :
    ViewModel() {


    var entryState by mutableStateOf(WeightEntryState())
        private set


    fun onWeightValueChanged(newWeightValue: String) {
        entryState = entryState.copy(
            weightValue = newWeightValue
        )

        validateInputs()
    }

    fun onWeightValueNotesChanged(newNotesVal: String) {
        entryState = entryState.copy(
            weightNotesValue = newNotesVal
        )
    }

    fun onUnitsChanged(newUnits: InputUnits) {
        entryState = entryState.copy(weightUnits = newUnits)
    }

    fun onWeightExtraSelected(weightExtra: WeightExtras, selectionVal: Boolean) {

        if (selectionVal) {
            val updateAbleList = entryState.selectedExtras.toMutableList()
            updateAbleList.add(weightExtra);
            entryState = entryState.copy(selectedExtras = updateAbleList)

        } else {
            val updateAbleList = entryState.selectedExtras.filter { we -> we != weightExtra }
            entryState = entryState.copy(selectedExtras = updateAbleList)
        }
    }

    fun onSavePressed() {
        viewModelScope.launch {
            val weightValue = entryState.weightValue.toDouble();

            val extras: List<WeightExtras> = entryState.selectedExtras;

            val newWeight =
                Weight(weightValue, entryState.weightUnits, System.currentTimeMillis(), extras)
            addNewWeightUseCase(newWeight)
        }

    }

    private fun validateInputs() {
        val validateWeightUseCaseResult =
            validateWeightUseCase(entryState.weightValue, entryState.weightUnits)

        when (validateWeightUseCaseResult) {
            WeightValidationType.WEIGHT_TOO_LOW -> {
                val stringResource = UiText.StringResource(R.string.weight_too_low)
                entryState =
                    entryState.copy(
                        weightValueError = stringResource,
                        weightValueValid = false
                    )
            }

            WeightValidationType.WEIGHT_TOO_HIGH -> TODO()
            WeightValidationType.WEIGHT_INCOMPLETE -> {

                val stringResource = UiText.StringResource(R.string.weight_incompete)
                entryState =
                    entryState.copy(
                        weightValueError = stringResource,
                        weightValueValid = false
                    )
            }

            WeightValidationType.EMPTY_FIELD -> TODO()
            WeightValidationType.VALID -> {
                entryState =
                    entryState.copy(
                        weightValueValid = true
                    )
            }
        }
    }
}