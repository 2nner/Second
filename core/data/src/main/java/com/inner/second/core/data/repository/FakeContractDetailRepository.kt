package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeContractDetailRepository @Inject constructor() : ContractDetailRepository {
    override fun fetchContract(contractId: Int): Flow<Contract> {
        return flowOf(
            when (contractId) {
                1 -> Contract.dummy()
                2 -> Contract.dummy2()
                else -> Contract.dummy()
            }
        )
    }
}