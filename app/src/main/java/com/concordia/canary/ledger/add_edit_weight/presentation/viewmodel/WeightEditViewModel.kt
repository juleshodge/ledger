package com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

import com.concordia.canary.ledger.add_edit_weight.domain.use_case.LoadSavedWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.UpdateSavedWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.ValidateWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.presentation.state.WeightEditState
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.InactivateWeightUseCase
import com.concordia.canary.ledger.util.GeneralEvent
import com.concordia.canary.ledger.util.ScreenRoutes

@HiltViewModel
class WeightEditViewModel @Inject constructor(
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val updateSavedWeightUseCase: UpdateSavedWeightUseCase,
    private val inactiveWeightUseCase: InactivateWeightUseCase,
    private val loadUseCase: LoadSavedWeightUseCase,
) : ViewModel() {

    var editState by mutableStateOf(
        WeightEditState()
    )
        private set

    private var _sendEvent: (GeneralEvent) -> Unit = {}

    fun setEventMethod(sendEvent: (GeneralEvent) -> Unit) {
        _sendEvent = sendEvent
    }

    fun onDelete(weightId: Long) {
        viewModelScope.launch {

            try {
                inactiveWeightUseCase.invoke(weightId, false)
            } catch (e: Exception) {
                _sendEvent(GeneralEvent.Error("Error changing weight status: ${e.message}"))
                return@launch
            }
            _sendEvent(GeneralEvent.NavToRoute(ScreenRoutes.WeightTrendPane))
        }
    }

    fun onUpdate() {
        viewModelScope.launch {
            val weightValue = editState.weightValue.toDouble();

            val extras: List<WeightExtras> = editState.selectedExtras;

            val updatedWeight =
                Weight(
                    weightId = editState.weightId,
                    weightValue,
                    editState.weightUnits,
                    editState.weightObsTime,
                    extras,
                    notes = editState.weightNotesValue,
                    active = true
                )

            try {
                updateSavedWeightUseCase.invoke(updatedWeight)
            } catch (e: Exception) {
                _sendEvent(GeneralEvent.Error("Error Updating Weight: ${e.message}"))

                return@launch
            }

            _sendEvent(GeneralEvent.NavToRoute(ScreenRoutes.WeightTrendPane))
        }
    }

    fun onUnitsChanged(newUnits: InputUnits) {
        editState = editState.copy(weightUnits = newUnits)
    }

    fun onWeightExtraSelected(weightExtra: WeightExtras, selectionVal: Boolean) {

        if (selectionVal) {
            val updateAbleList = editState.selectedExtras.toMutableList()
            updateAbleList.add(weightExtra);
            editState = editState.copy(selectedExtras = updateAbleList)

        } else {
            val updateAbleList = editState.selectedExtras.filter { we -> we != weightExtra }
            editState = editState.copy(selectedExtras = updateAbleList)
        }
    }

    fun onLoadWeightEntry(weightId: Long) {
        viewModelScope.launch {
            val weightItem = loadUseCase.invoke(weightId = weightId)

            weightItem.collect(FlowCollector { weight ->
                editState = when (weight) {
                    is Resource.Error -> {

                        editState.copy(loading = false, loadFailed = true)
                    }

                    is Resource.Loading -> {
                        editState.copy(loading = true)
                    }

                    is Resource.Success -> {
                        val weightEntry = weight.data!!
                        editState.copy(
                            changeMade = false,
                            loading = false,
                            weightValue = weightEntry.weightValue.toString(),
                            weightObsTime = weightEntry.observationDate,
                            weightNotesValue = weightEntry.notes,
                            weightUnits = weightEntry.units,
                            weightValueValid = true,
                            selectedExtras = weightEntry.extras,
                            weightId = weightEntry.weightId
                        )
                    }
                }
            })
        }
    }

    fun onWeightValueChanged(newWeightValue: String) {
        editState = editState.copy(
            weightValue = newWeightValue
        )

        validateWeightUseCase.invoke(
            editState.weightValue,
            editState.weightUnits,
            editState.changeMade
        )
    }

    fun onWeightValueNotesChanged(newNotesVal: String) {
        editState = editState.copy(
            weightNotesValue = newNotesVal
        )
    }
}