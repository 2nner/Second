package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeContractDetailRepository @Inject constructor(
    private val homeRepository: HomeRepository,
) : ContractDetailRepository {
    override fun fetchContract(contractId: Int): Flow<Contract> {
        return homeRepository.fetchContractList()
            .map {
                runCatching {
                    it.first { it.id == contractId }
                }
                    .getOrElse { Contract.dummy() }
            }
    }
}