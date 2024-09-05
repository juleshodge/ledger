package com.concordia.canary.ledger.add_edit_weight.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.concordia.canary.ledger.core.data.local.WeightDao
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.repository.WeightRepository
import com.concordia.canary.ledger.util.Resource

class WeightRepositoryImpl(private val dao: WeightDao) : WeightRepository {

    override suspend fun loadAll(): Flow<Resource<List<Weight>>> = flow {
        emit(Resource.Loading())

        emit(Resource.Success(dao.getAll().map { it.toWeightModel() }))
    }

    override suspend fun add(weight: Weight) {
        val toEntity = weight.toEntity()
        dao.insert(toEntity)
    }

    override suspend fun getById(id: Long): Flow<Resource<Weight>> = flow {
        emit(Resource.Loading())

        emit(Resource.Success(dao.getById(id).toWeightModel()))
    }

    override suspend fun updatedActive(id: Long, newVal: Boolean) {
        dao.updateStatus(id, status = newVal)
    }
}