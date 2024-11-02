package com.concordia.canary.ledger.core.domain.repository


import com.concordia.canary.ledger.core.domain.model.TrendSettings

interface TrendSettingsRepository {
    suspend fun getTrendSettings() : TrendSettings

    suspend fun updateTrendSettings(newSettings: TrendSettings) : Unit
}