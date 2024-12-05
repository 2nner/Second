package com.inner.second.core.model

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
            title = "금전소비대차계약서",
            type = ContractType.New,
            duration = DateDuration(),
            createdAt = "10.9 월 오후 1:12",
            dueDate = "D-5",
            name = "홍길동",
            oppositeName = "김아무개",
            description = null,
            isMine = false
        )
    }
}
