package com.concordia.canary.ledger.trend_settings_edit.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.use_case.LoadSelectableUnitsUseCase
import com.concordia.canary.ledger.trend_settings_edit.presentation.state.TrendSettingsState

@HiltViewModel
class TrendSettingsViewModel @Inject constructor(private val selectableTrendUnitsUseCase: LoadSelectableUnitsUseCase) :
    ViewModel() {


    var state by mutableStateOf(TrendSettingsState())
        private set

    fun getAvailSelectableUnits(): List<TrendSelectedUnits> {
        return selectableTrendUnitsUseCase.invoke()
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
}