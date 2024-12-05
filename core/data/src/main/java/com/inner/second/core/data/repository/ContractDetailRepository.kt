package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow

interface ContractDetailRepository {
    fun fetchContract(contractId: Int): Flow<Contract>
}
