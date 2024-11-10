package com.inner.second.core.model

import androidx.compose.runtime.Stable

@Stable
sealed class ContractForm {
    open fun inputs(): List<ContractFormInput> = listOf()

    data object New : ContractForm() {
        override fun inputs(): List<ContractFormInput> {
            return listOf(
                ContractFormInput.ContractTitle,
                ContractFormInput.MultipleDescription,
                ContractFormInput.DateDuration,
            )
        }
    }

    data object IOU : ContractForm() {
        override fun inputs(): List<ContractFormInput> {
            return listOf(
                ContractFormInput.ContractTitle,
                ContractFormInput.Selection(options = listOf("금전", "물건")),
                ContractFormInput.OpponentName,
                ContractFormInput.Price,
                ContractFormInput.DateDuration,
            )
        }
    }
}