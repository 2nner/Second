package com.inner.second.core.data.repository

import com.inner.second.core.model.Contract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeHomeRepository @Inject constructor() : HomeRepository {

    private val contractList: Flow<List<Contract>> = flowOf(
        mutableListOf(
            Contract(
                id = 1,
                title = "금전소비대차계약서",
                createdAt = "10.9 월 오후 1:12",
                dueDate = "D-5"
            ),
            Contract(
                id = 2,
                title = "금전소비대차계약서",
                createdAt = "10.9 월 오후 1:12",
                dueDate = "D-5"
            ),
        )
    )

    override fun fetchContractList(): Flow<List<Contract>> {
        return this.contractList
    }
}