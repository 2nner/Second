package com.inner.second.core.model

import androidx.compose.runtime.Stable
import kotlin.reflect.KClass

@Stable
sealed class ContractFormInput(
    val title: String,
    val type: ContractFormInputType,
    val dataType: KClass<*>,
) {
    val key: String = this::class.java.simpleName

    data object ContractTitle : ContractFormInput(
        title = "계약서 이름",
        type = ContractFormInputType.Text,
        dataType = String::class,
    )

    data object DateDuration : ContractFormInput(
        title = "기간",
        type = ContractFormInputType.DateDuration,
        dataType = com.inner.second.core.model.DateDuration::class
    )

    data object MultipleDescription : ContractFormInput(
        title = "전문",
        type = ContractFormInputType.MultiText,
        dataType = List::class
    )

    data class Selection(
        val options: List<String>,
    ) : ContractFormInput(
        title = "유형",
        type = ContractFormInputType.RadioButton(options),
        dataType = String::class
    )

    data object OpponentName : ContractFormInput(
        title = "상대방 이름",
        type = ContractFormInputType.Text,
        dataType = String::class
    )

    data object ItemName : ContractFormInput(
        title = "빌려줄 물건",
        type = ContractFormInputType.Text,
        dataType = String::class
    )

    data object Price : ContractFormInput(
        title = "빌려줄 금액",
        type = ContractFormInputType.Price,
        dataType = String::class
    )
}
