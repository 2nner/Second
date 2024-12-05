package com.inner.second.core.model

import java.time.LocalDateTime

data class Contract(
    val id: Int,
    val title: String,
    val type: ContractType,
    val duration: DateDuration,
    val createdAt: String,
    val dueDate: String?,
    val name: String,
    val oppositeName: String,
    val description: String?,
    val isMine: Boolean,
) {
    val contractConcluded: Boolean = dueDate == null

    companion object {
        fun dummy(): Contract = Contract(
            id = 1,
            title = "금전계약서",
            type = ContractType.IOU,
            duration = DateDuration(
                startDate = LocalDateTime.of(2024, 10, 9, 13, 12),
                endDate = LocalDateTime.of(2024, 11, 9, 13, 12)
            ),
            createdAt = "10.9 월 오후 1:12",
            dueDate = "D-5",
            name = "김경택",
            oppositeName = "김한슬",
            description = "5만원",
            isMine = false
        )

        fun dummy2(): Contract = Contract(
            id = 2,
            title = "닌텐도 스위치 7일 이용권",
            type = ContractType.New,
            duration = DateDuration(
                startDate = LocalDateTime.of(2024, 12, 6, 13, 12),
                endDate = LocalDateTime.of(2025, 12, 5, 13, 12),
            ),
            createdAt = "12.6 금 오전 0:00",
            dueDate = null,
            name = "김한슬",
            oppositeName = "김아무개",
            description = "닌텐도 스위치",
            isMine = true
        )
    }
}
