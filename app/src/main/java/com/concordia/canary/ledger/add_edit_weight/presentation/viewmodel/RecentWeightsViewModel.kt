package com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.LoadRecentWeightsUseCase
import com.concordia.canary.ledger.add_edit_weight.presentation.state.RecentWeightsState
import com.concordia.canary.ledger.util.Resource

@HiltViewModel
class RecentWeightsViewModel @Inject constructor(private val loadRecentWeightsUseCase: LoadRecentWeightsUseCase) :
    ViewModel() {

    var recentsState by mutableStateOf(RecentWeightsState())
        private set

    fun loadRecentWeights() {
        viewModelScope.launch {
            loadRecentWeightsUseCase.invoke().collect(FlowCollector { result ->

                recentsState = when (result) {
                    is Resource.Success -> {
                        recentsState.copy(
                            recentWeights = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        TODO()
                    }

                    is Resource.Loading -> {
                        recentsState.copy(isLoading = true)
                    }
                }
            })
        }
    }
}