package com.concordia.canary.ledger.weight_trends.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.concordia.canary.ledger.core.data.local.WeightDao
import com.concordia.canary.ledger.util.Resource
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.domain.repository.WeightTrendRepository

class WeightTrendRepositoryImpl(private val dao: WeightDao) : WeightTrendRepository {
    override suspend fun loadAllActive(): Flow<Resource<List<TrendWeight>>> = flow {
        emit(Resource.Loading())

        emit(Resource.Success(dao.getAll()
            .filter { w -> w.active }
            .map { entity ->
                TrendWeight.entityMapper(entity)
            })
        )
    }
}