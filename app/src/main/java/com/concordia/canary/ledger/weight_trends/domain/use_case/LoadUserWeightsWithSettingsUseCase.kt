package com.concordia.canary.ledger.weight_trends.domain.use_case

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import com.concordia.canary.ledger.core.util.TrendWeightConverter
import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.core.domain.model.TrendSettings
import com.concordia.canary.ledger.core.domain.repository.TrendSettingsRepository
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.domain.repository.WeightTrendRepository

class LoadUserWeightsWithSettingsUseCase(
    private val weightTrendRepository: WeightTrendRepository,
    private val trendWeightConverter: TrendWeightConverter,
    private val trendSettingsRepository: TrendSettingsRepository
) {
    suspend fun invoke(): Flow<Resource<List<TrendWeight>>> {
        val trendSettings = trendSettingsRepository.getTrendSettings()
        return weightTrendRepository.loadAllActive().map { activeWeightsResource ->
            transformList(activeWeightsResource, trendSettings)
        }
    }

    private fun transformList(
        activeWeights: Resource<List<TrendWeight>>,
        settings: TrendSettings
    ): Resource<List<TrendWeight>> {

        if (activeWeights is Resource.Loading) {
            return activeWeights
        }

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -settings.daysBack)
        val rangeStart = calendar.time
        val weights = activeWeights.data!!.filter { obs -> obs.observationDate >= rangeStart.time }

        val preferredUnits = settings.preferredUnits

        val convertedWeights =
            weights.map { w -> trendWeightConverter.transformWeight(w, preferredUnits) }

        return Resource.Success(convertedWeights)
    }
}