package com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel

import javax.inject.Inject
import java.time.Instant
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightValidationType
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.AddNewWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.ValidateWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.presentation.state.WeightEntryState
import com.concordia.canary.ledger.util.GeneralEvent
import com.concordia.canary.ledger.util.ScreenRoutes
import com.concordia.canary.ledger.util.UiText

@HiltViewModel
class WeightEntryViewModel @Inject constructor(
    val validateWeightUseCase: ValidateWeightUseCase,
    val addNewWeightUseCase: AddNewWeightUseCase
) :
    ViewModel() {

    var entryState by mutableStateOf(WeightEntryState())
        private set

    init {
        if (entryState.weightObsTime == 0L) {
            val timeInMillis = Instant.now().toEpochMilli()
            onWeightObsTimeChange(timeInMillis)
        }
    }

    private var _sendEvent: (GeneralEvent) -> Unit = {}

    fun setEventMethod(sendEvent: (GeneralEvent) -> Unit) {
        _sendEvent = sendEvent
    }

    fun onWeightObsTimeChange(newWeightTime: Long) {
        entryState = entryState.copy(
            weightObsTime = newWeightTime
        )

        validateInputs()
    }

    fun onWeightValueChanged(newWeightValue: String) {
        entryState = entryState.copy(
            weightValue = newWeightValue,
            changeMade = true
        )

        validateInputs()
    }

    fun onWeightValueNotesChanged(newNotesVal: String) {
        entryState = entryState.copy(
            weightNotesValue = newNotesVal,
            changeMade = true
        )
    }

    fun onUnitsChanged(newUnits: InputUnits) {
        entryState = entryState.copy(
            weightUnits = newUnits,
            changeMade = true
        )
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

    fun onSavePressed(saveMessage: String) {
        viewModelScope.launch {
            val weightValue = entryState.weightValue.toDouble();

            val extras: List<WeightExtras> = entryState.selectedExtras;

            val newWeight =
                Weight(
                    null,
                    weightValue,
                    entryState.weightUnits,
                    entryState.weightObsTime,
                    extras,
                    notes = entryState.weightNotesValue,
                    active = true
                )

            try {
                addNewWeightUseCase(newWeight)
            } catch (e: Exception) {
                _sendEvent(GeneralEvent.Error("Error Saving Weight: ${e.message}"))

                return@launch
            }

            _sendEvent(GeneralEvent.WeightAdded(saveMessage))
            _sendEvent(GeneralEvent.NavToRoute(ScreenRoutes.WeightTrendPane))
        }
    }

    private fun validateInputs() {
        val validateWeightUseCaseResult =
            validateWeightUseCase(
                entryState.weightValue,
                entryState.weightUnits,
                entryState.changeMade
            )

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

            WeightValidationType.OBS_TIME_INCOMPLETE -> {
                entryState = entryState.copy(weightValueValid = false)
            }
        }
    }
}