package com.concordia.canary.ledger.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import com.concordia.canary.ledger.util.GeneralEvent
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor() :
    ViewModel() {

    private val _generalEventMutableFlow = Channel<GeneralEvent>()
    val generalEventFlow = _generalEventMutableFlow.receiveAsFlow()

    fun sendGeneralEvent(generalEvent: GeneralEvent) {
        viewModelScope.launch {
            _generalEventMutableFlow.send(generalEvent)
        }
    }
}