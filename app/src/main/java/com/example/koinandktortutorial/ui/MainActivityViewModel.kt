package com.example.koinandktortutorial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinandktortutorial.domain.NetworkResponse
import com.example.koinandktortutorial.domain.usecase.GetListUseCase
import com.example.koinandktortutorial.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainActivityViewModel(
    ) : ViewModel() {

    private val getListUseCase: GetListUseCase by inject(GetListUseCase::class.java)
    private val _uiState = MutableStateFlow<UIState?>(null)
    val uiState: StateFlow<UIState?> get() = _uiState

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            getListUseCase.getList().collectLatest {
                when (it) {
                    is NetworkResponse.Success -> {
                        _uiState.value = UIState.OnDisplayData(it.data)
                    }
                    is NetworkResponse.Loading -> {
                        _uiState.value = UIState.OnLoading
                    }
                    else -> {
                        _uiState.value = UIState.OnError(it.message)
                    }
                }
            }
        }
    }

}