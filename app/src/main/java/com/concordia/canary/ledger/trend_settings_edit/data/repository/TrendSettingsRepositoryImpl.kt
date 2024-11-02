package com.concordia.canary.ledger.trend_settings_edit.data.repository

import com.concordia.canary.ledger.core.data.local.TrendSelectedUnitsDao
import com.concordia.canary.ledger.core.data.local.TrendSelectedUnitsEntity
import com.concordia.canary.ledger.core.data.local.TrendSettingsDao
import com.concordia.canary.ledger.core.data.local.TrendSettingsEntity
import com.concordia.canary.ledger.core.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.core.domain.model.TrendSettings
import com.concordia.canary.ledger.core.domain.repository.TrendSettingsRepository

class TrendSettingsRepositoryImpl(
    private val trendSettingsDao: TrendSettingsDao,
    private val trendSelectedUnitsDao: TrendSelectedUnitsDao
) : TrendSettingsRepository {

    override suspend fun getTrendSettings(): TrendSettings {

        val settingsEntity = trendSettingsDao.get()

        if (settingsEntity != null) {
            val selectedUnitsEntity = trendSelectedUnitsDao.get()
            val toModel = settingsEntity.toModel(selectedUnitsEntity)

            return toModel
        }

        return TrendSettings()
    }

    private fun toSelectableUnitsEntity(newSelectedPreferred: TrendSelectedUnits): TrendSelectedUnitsEntity {


        return TrendSelectedUnitsEntity(
            null,
            newSelectedPreferred.numericType,
            newSelectedPreferred.subType,
            "", System.currentTimeMillis()
        )

    }

    override suspend fun updateTrendSettings(newSettings: TrendSettings) {

        val todayNow = System.currentTimeMillis()

        val trendSettingsEntity = TrendSettingsEntity(null, newSettings.daysBack, todayNow)

        val toSelectableUnitsEntity = toSelectableUnitsEntity(newSettings.preferredUnits)


        val settingsEntity = trendSettingsDao.insert(trendSettingsEntity)

        trendSelectedUnitsDao.update(toSelectableUnitsEntity)
    }
}