package com.concordia.canary.ledger.weight_trends.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.channels.Channel

import com.concordia.canary.ledger.weight_trends.presentation.state.WeightTrendsState
import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeightEvent
import com.concordia.canary.ledger.weight_trends.domain.use_case.CalculateSevenDayTrendUseCase
import com.concordia.canary.ledger.weight_trends.domain.use_case.LoadUserWeightsWithSettingsUseCase


@HiltViewModel
class WeightTrendsViewModel @Inject constructor(
    private val calcTrend: CalculateSevenDayTrendUseCase,
    private val userWeightsWithSettingsUseCase: LoadUserWeightsWithSettingsUseCase
) :
    ViewModel() {

    var trendsState by mutableStateOf(WeightTrendsState())
        private set

    init {
        loadRecentTrendWeights()
    }

    private val _trendChannel = Channel<TrendWeightEvent>()

    val trendEvents = _trendChannel.receiveAsFlow()

    fun sendTrendEvent(event: TrendWeightEvent) {
        viewModelScope.launch {
            _trendChannel.send(event)
        }
    }

    fun loadRecentTrendWeights() {
        viewModelScope.launch {
            userWeightsWithSettingsUseCase.invoke().collect(FlowCollector { result ->

                trendsState = when (result) {
                    is Resource.Loading -> {
                        trendsState.copy(isLoading = true)
                    }

                    is Resource.Error -> TODO()
                    is Resource.Success -> {
                        val resVal = result.data ?: emptyList()
                        trendsState.copy(
                            trendWeights = resVal,
                            isLoading = false,
                            trendWeightStats = calcTrend.invoke(resVal)
                        )
                    }
                }
            })
        }
    }
}