package com.concordia.canary.ledger.add_edit_weight.domain.repository

import kotlinx.coroutines.flow.Flow

import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.util.Resource

interface WeightRepository {
    suspend fun loadAll(): Flow<Resource<List<Weight>>>

    suspend fun add(weight: Weight)

    suspend fun getById(id: Long): Flow<Resource<Weight>>

    suspend fun updatedActive(id: Long, newVal: Boolean)
}