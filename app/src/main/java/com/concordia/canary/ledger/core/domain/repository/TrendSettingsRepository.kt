package com.concordia.canary.ledger.trend_settings_edit.domain.repository


import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSettings

interface TrendSettingsRepository {
    suspend fun getTrendSettings() : TrendSettings

    suspend fun updateTrendSettings(newSettings: TrendSettings) : Unit
}