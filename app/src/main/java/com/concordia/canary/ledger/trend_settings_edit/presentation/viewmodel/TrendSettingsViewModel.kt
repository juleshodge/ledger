package com.concordia.canary.ledger.trend_settings_edit.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.concordia.canary.ledger.core.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.core.domain.model.TrendSettings
import com.concordia.canary.ledger.trend_settings_edit.domain.use_case.LoadSelectableUnitsUseCase
import com.concordia.canary.ledger.trend_settings_edit.domain.use_case.LoadUserSettingsUseCase
import com.concordia.canary.ledger.trend_settings_edit.domain.use_case.UpdateUserSettingsUseCase
import com.concordia.canary.ledger.trend_settings_edit.presentation.state.TrendSettingsState
import com.concordia.canary.ledger.util.GeneralEvent
import com.concordia.canary.ledger.util.ScreenRoutes

@HiltViewModel
class TrendSettingsViewModel @Inject constructor(
    private val selectableTrendUnitsUseCase: LoadSelectableUnitsUseCase,
    private val loadUserSettingsUseCase: LoadUserSettingsUseCase,
    private val updateUserSettingsUseCase: UpdateUserSettingsUseCase
) :
    ViewModel() {

    init {
        viewModelScope.launch {
            val settings = loadUserSettingsUseCase.invoke()
            state = state.copy(
                sel = settings.preferredUnits,
                daysBack = settings.daysBack
            )
        }
    }

    var state by mutableStateOf(TrendSettingsState())
        private set

    private var _sendEvent: (GeneralEvent) -> Unit = {}


    fun getAvailSelectableUnits(): List<TrendSelectedUnits> {
        return selectableTrendUnitsUseCase.invoke()
    }

    fun setEventMethod(sendEvent: (GeneralEvent) -> Unit) {
        _sendEvent = sendEvent
    }

    fun updateTrendUnitSelection(updatedUnits: TrendSelectedUnits) {
        state = state.copy(
            sel = updatedUnits
        )
    }

    fun onDaysBackChange(newVal: String) {
        val daysBackVal = newVal.toInt()
        state = state.copy(
            daysBack = daysBackVal
        )
    }

    fun onSave() {
        val newSettings = TrendSettings(state.sel, state.daysBack)
        viewModelScope.launch {
            try {
                updateUserSettingsUseCase.invoke(newSettings);
                val evt = GeneralEvent.NavToRoute(ScreenRoutes.WeightTrendPane)
                _sendEvent.invoke(evt)
            } catch (e: Exception) {
                if (e.message.isNullOrBlank()) {
                    val evt = GeneralEvent.Error("Error saving settings")
                    _sendEvent.invoke(evt)
                }

                val evt = GeneralEvent.Error("Error saving settings ${e.message}")
                _sendEvent.invoke(evt)
            }
        }
    }
}