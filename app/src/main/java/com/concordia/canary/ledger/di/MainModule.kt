package com.concordia.canary.ledger.di

import com.concordia.canary.ledger.add_edit_weight.domain.use_case.ValidateWeightUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesValidateWeightUseCase(): ValidateWeightUseCase {
        return ValidateWeightUseCase()
    }
}