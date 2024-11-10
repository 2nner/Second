package com.inner.second.feature.contract

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inner.second.core.model.ContractType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewContractViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val contractType: StateFlow<ContractType> =
        savedStateHandle.getStateFlow<ContractType>(CONTRACT_TYPE, ContractType.New)
    private val contractFormMap: MutableStateFlow<Map<String, Any>> = MutableStateFlow(emptyMap())

    fun onContractFormChange(key: String, value: Any) {
        viewModelScope.launch {
            contractFormMap.getAndUpdate {
                it.toMutableMap().apply {
                    this[key] = value
                }
                    .also {
                        Log.d("contractFormMap", it.toString())
                    }
            }
        }
    }

    init {
        viewModelScope.launch {
            contractType.collectLatest { contractFormMap.emit(emptyMap()) }
        }
    }

    companion object {
        private const val CONTRACT_TYPE = "contractType"
    }
}