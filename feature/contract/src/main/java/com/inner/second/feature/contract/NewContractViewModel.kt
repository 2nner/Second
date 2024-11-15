package com.inner.second.feature.contract

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inner.second.core.data.user.ContractRepository
import com.inner.second.core.data.user.UserRepository
import com.inner.second.core.model.ContractFormInput
import com.inner.second.core.model.ContractKey
import com.inner.second.core.model.ContractType
import com.inner.second.core.util.getHtmlForm
import com.inner.second.core.util.toEncodedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewContractViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val contractRepository: ContractRepository,
) : ViewModel() {

    val contractType: StateFlow<ContractType> =
        savedStateHandle.getStateFlow<ContractType>(CONTRACT_TYPE, ContractType.New)

    private val contractFormMap: MutableStateFlow<Map<String, Any>> = MutableStateFlow(emptyMap())

    private val _contractForm: MutableStateFlow<String> = MutableStateFlow("")
    val contractForm = _contractForm.asStateFlow()

    val contractFile: MutableStateFlow<File?> = MutableStateFlow(null)

    fun onContractFormMapChange(key: String, value: Any) {
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

    fun onContractTypeChange(contractType: ContractType) {
        savedStateHandle[CONTRACT_TYPE] = contractType
    }

    fun onContractFormUpdate() {
        viewModelScope.launch {
            combine(
                contractType,
                contractFormMap.filter { it.isNotEmpty() },
                userRepository.fetchUser()
            ) { type, map, user ->
                type.getHtmlForm(map, user)
            }
                .take(1)
                .collectLatest { _contractForm.emit(it) }
        }
    }

    fun onSignatureComplete(bitmap: ImageBitmap) {
        viewModelScope.launch {
            onContractFormMapChange(
                key = ContractKey.Signature.name,
                value = bitmap.toEncodedString()
            )
            onContractFormUpdate()
        }
    }

    fun onContractFormSubmit() {
        viewModelScope.launch {
            val title = contractFormMap.value[ContractFormInput.ContractTitle.key] as String
            val html = contractForm.value

            val file = contractRepository.exportHtmlToPdf(
                title = title,
                html = html,
            )
            contractFile.emit(file)
        }
    }
    
    fun saveContract(uri: Uri) {
        viewModelScope.launch {
            contractFile.value?.let { contractFile ->
                contractRepository.saveContract(uri = uri, file = contractFile)
            }
        }
    }

    init {
        viewModelScope.launch {
            contractType.collectLatest { contractFormMap.emit(emptyMap()) }
        }

        viewModelScope.launch {
            contractType.collectLatest {
                Log.d("TAG", it.toString())
            }
        }
    }

    companion object {
        private const val CONTRACT_TYPE = "contractType"
    }
}