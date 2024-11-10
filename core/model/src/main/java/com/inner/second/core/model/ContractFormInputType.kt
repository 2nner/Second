package com.inner.second.core.model

import androidx.compose.runtime.Stable

@Stable
sealed interface ContractFormInputType {
    data object Text : ContractFormInputType
    data object Price : ContractFormInputType
    data object DateDuration : ContractFormInputType
    data class RadioButton(val options: List<String>) : ContractFormInputType
    data object MultiText : ContractFormInputType
}