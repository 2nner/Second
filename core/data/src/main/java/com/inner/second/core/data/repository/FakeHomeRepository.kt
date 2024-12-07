package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FakeHomeRepository @Inject constructor() : HomeRepository {

    private val contractList = MutableStateFlow(
        listOf(
            Contract.dummy(),
            Contract.dummy2()
        )
    )

    override fun fetchContractList(): Flow<List<Contract>> {
        return this.contractList
    }

    override fun addContract(contract: Contract) {
        val newList = contractList.value.toMutableList().plus(contract)

        CoroutineScope(Dispatchers.IO).launch {
            contractList.emit(newList)
        }
    }
}