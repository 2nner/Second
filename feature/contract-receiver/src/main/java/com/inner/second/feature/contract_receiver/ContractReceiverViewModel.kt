package com.inner.second.feature.contract_receiver

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.inner.second.core.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContractReceiverViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeRepository: HomeRepository,
) : ViewModel() {

    // val contractId = savedStateHandle.toRoute<SecondScreen.ContractReceiver>()
    private var _confirmed = MutableStateFlow(false)
    val confirmed = _confirmed.asStateFlow()

    fun updateConfirmedState(state: Boolean) {
        _confirmed.value = state
    }

    // Only for dummy test
    fun updateContractStateConcluded() {
        homeRepository.updateContractStateConcluded(1)
    }
}