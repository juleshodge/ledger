package com.concordia.canary.ledger.trend_settings_edit.domain.use_case

import com.concordia.canary.ledger.core.domain.model.TrendSettings
import com.concordia.canary.ledger.core.domain.repository.TrendSettingsRepository

class LoadUserSettingsUseCase(private val settingsRepository: TrendSettingsRepository) {

    suspend fun invoke(): TrendSettings {
        return settingsRepository.getTrendSettings()
    }
}