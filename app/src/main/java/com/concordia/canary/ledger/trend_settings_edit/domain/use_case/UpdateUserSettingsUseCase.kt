package com.concordia.canary.ledger.trend_settings_edit.domain.use_case

import com.concordia.canary.ledger.core.domain.model.TrendSettings
import com.concordia.canary.ledger.core.domain.repository.TrendSettingsRepository

class UpdateUserSettingsUseCase(private val settingsRepository: TrendSettingsRepository) {
    suspend fun invoke(newSettings: TrendSettings) {
        return settingsRepository.updateTrendSettings(newSettings)
    }
}