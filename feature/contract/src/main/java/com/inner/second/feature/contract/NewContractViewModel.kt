package com.inner.second.feature.contract

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.inner.second.core.navigation.ContractType
import com.inner.second.core.navigation.SecondScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewContractViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val contractType = requireNotNull(savedStateHandle.get<ContractType>(CONTRACT_TYPE)) {
        "contractType is required"
    }

    companion object {
        private const val CONTRACT_TYPE = "contractType"
    }
}