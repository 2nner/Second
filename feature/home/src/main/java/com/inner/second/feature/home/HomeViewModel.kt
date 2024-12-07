package com.inner.second.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inner.second.core.data.repository.HomeRepository
import com.inner.second.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val contractList = homeRepository.fetchContractList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    val user = userRepository.fetchUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
}