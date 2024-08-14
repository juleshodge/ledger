package com.concordia.canary.ledger.add_edit_weight.data.repository


import com.concordia.canary.ledger.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.concordia.canary.ledger.add_edit_weight.data.local.WeightDao
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository

class WeightRepositoryImpl(private val dao: WeightDao) : WeightRepository {

    override suspend fun loadAll(): Flow<Resource<List<Weight>>> = flow {
        emit(Resource.Loading())

        emit(Resource.Success(dao.getAll().map { it.toWeightModel() }))

    }

    override suspend fun add(weight: Weight) {
        val toEntity = weight.toEntity()
        dao.insert(toEntity)
    }
}