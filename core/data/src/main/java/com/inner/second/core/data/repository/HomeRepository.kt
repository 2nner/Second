package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun fetchContractList(): Flow<List<Contract>>
    fun addContract(contract: Contract)
}