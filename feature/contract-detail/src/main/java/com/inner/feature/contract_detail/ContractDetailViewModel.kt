package com.inner.feature.contract_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.inner.second.core.data.repository.ContractDetailRepository
import com.inner.second.core.model.Contract
import com.inner.second.core.navigation.SecondScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContractDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val contractDetailRepository: ContractDetailRepository,
) : ViewModel() {

    private val contractId = savedStateHandle.toRoute<SecondScreen.ContractDetail>().contractId

    val contract = contractDetailRepository.fetchContract(contractId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}