package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeHomeRepository @Inject constructor() : HomeRepository {

    private val contractList: Flow<List<Contract>> = flowOf(
        mutableListOf(
            Contract.dummy(),
            Contract.dummy().copy(
                id = 2
            ),
        )
    )

    override fun fetchContractList(): Flow<List<Contract>> {
        return this.contractList
    }
}