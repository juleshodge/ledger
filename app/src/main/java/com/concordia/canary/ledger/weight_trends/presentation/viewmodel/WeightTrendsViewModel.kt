package com.concordia.canary.ledger.weight_trends.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.concordia.canary.ledger.util.Resource
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import com.concordia.canary.ledger.weight_trends.domain.use_case.LoadUserWeightsUseCase
import com.concordia.canary.ledger.weight_trends.presentation.state.WeightTrendsState


@HiltViewModel
class WeightTrendsViewModel @Inject constructor(private val loadUserWeightsUseCase: LoadUserWeightsUseCase) :
    ViewModel() {

    var trendsState by mutableStateOf(WeightTrendsState())
        private set

    fun loadRecentTrendWeights() {
        viewModelScope.launch {
            loadUserWeightsUseCase.invoke().collect(FlowCollector { result ->

                trendsState = when (result) {
                    is Resource.Loading -> {
                        trendsState.copy(isLoading = true)
                    }

                    is Resource.Error -> TODO()
                    is Resource.Success -> {
                        trendsState.copy(
                            trendWeights = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            })
        }
    }
}