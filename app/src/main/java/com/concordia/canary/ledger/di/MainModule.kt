package com.concordia.canary.ledger.di


import android.app.Application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room

import com.concordia.canary.ledger.add_edit_weight.domain.use_case.ValidateWeightUseCase
import com.concordia.canary.ledger.core.data.local.WeightDatabase
import com.concordia.canary.ledger.add_edit_weight.data.repository.WeightRepositoryImpl
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.AddNewWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.InactivateWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.LoadRecentWeightsUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.LoadSavedWeightUseCase
import com.concordia.canary.ledger.add_edit_weight.domain.use_case.UpdateSavedWeightUseCase
import com.concordia.canary.ledger.util.WeightConverter
import com.concordia.canary.ledger.weight_trends.data.repository.WeightTrendRepositoryImpl
import com.concordia.canary.ledger.weight_trends.domain.repository.WeightTrendRepository
import com.concordia.canary.ledger.weight_trends.domain.use_case.CalculateSevenDayTrendUseCase
import com.concordia.canary.ledger.weight_trends.domain.use_case.LoadUserWeightsUseCase

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesValidateWeightUseCase(): ValidateWeightUseCase {
        return ValidateWeightUseCase()
    }

    @Provides
    @Singleton
    fun provideAddNewWeightUseCase(weightRepository: WeightRepository): AddNewWeightUseCase {
        return AddNewWeightUseCase(weightRepository)
    }

    @Provides
    @Singleton
    fun calculateSevenDayTrendUseCase(): CalculateSevenDayTrendUseCase {
        return CalculateSevenDayTrendUseCase(converter = WeightConverter())
    }

    @Provides
    @Singleton
    fun provideWeightTrendRepositoryUseCase(weightTrendRepository: WeightTrendRepository): LoadUserWeightsUseCase {
        return LoadUserWeightsUseCase(weightTrendRepository)
    }

    @Provides
    @Singleton
    fun provideLoadSavedWeightUseCase(weightRepository: WeightRepository): LoadSavedWeightUseCase {
        return LoadSavedWeightUseCase(weightRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSavedWeightUseCase(weightRepository: WeightRepository): UpdateSavedWeightUseCase {
        return UpdateSavedWeightUseCase(weightRepository)
    }

    @Provides
    @Singleton
    fun provideInactivateWeightUseCase(weightRepository: WeightRepository): InactivateWeightUseCase {
        return InactivateWeightUseCase(weightRepository)
    }


    @Provides
    @Singleton
    fun provideRecentWeightsUseCase(weightRepository: WeightRepository): LoadRecentWeightsUseCase {
        return LoadRecentWeightsUseCase(weightRepository)
    }

    @Provides
    @Singleton
    fun provideWeightDatabase(app: Application): WeightDatabase {
        return Room.databaseBuilder(
            app,
            WeightDatabase::class.java,
            "weight_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeightRepository(database: WeightDatabase): WeightRepository {
        return WeightRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideWeightTrendRepository(database: WeightDatabase): WeightTrendRepository {
        return WeightTrendRepositoryImpl(database.dao)
    }
}