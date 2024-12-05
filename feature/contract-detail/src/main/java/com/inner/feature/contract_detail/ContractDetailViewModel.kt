package com.inner.feature.contract_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.inner.second.core.model.Contract
import com.inner.second.core.navigation.SecondScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContractDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val contractId = savedStateHandle.toRoute<SecondScreen.ContractDetail>().contractId

    private val _contract = MutableStateFlow<Contract?>(null)
    val contract = _contract.asStateFlow()

    init {
        _contract.value = when (contractId) {
            1 -> Contract.dummy().copy(description = "아이패드")
            else -> Contract.dummy()
        }
    }
}