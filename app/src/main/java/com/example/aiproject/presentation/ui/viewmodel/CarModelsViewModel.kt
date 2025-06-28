package com.example.aiproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiproject.domain.model.CarModel
import com.example.aiproject.domain.usecase.GetCarModelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarModelsViewModel @Inject constructor(
    private val getCarModelsUseCase: GetCarModelsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<List<CarModel>>(emptyList())
    val state: StateFlow<List<CarModel>> = _state.asStateFlow()

    init {
        fetchToyotaModels()
    }

    private fun fetchToyotaModels() {
        viewModelScope.launch {
            _state.value = getCarModelsUseCase.invoke()
        }
    }
}