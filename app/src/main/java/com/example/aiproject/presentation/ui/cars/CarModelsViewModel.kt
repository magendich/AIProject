package com.example.aiproject.presentation.ui.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _state = MutableStateFlow<List<CarItem>>(emptyList())
    val state: StateFlow<List<CarItem>> = _state.asStateFlow()

    init {
        fetchToyotaModels()
    }

    private fun fetchToyotaModels() {
        viewModelScope.launch {
            _state.value = getCarModelsUseCase.invoke().map {
                CarItem(
                    model = it.name,
                    brand = capitalizeFirstLetter(it.brand)
                )
            }
        }
    }

    private fun capitalizeFirstLetter(text: String): String {
        if (text.isEmpty()) return text
        val capitalizedFirstChar = if (text[0].isLowerCase()) text[0].titlecaseChar() else text[0]
        return capitalizedFirstChar + text.substring(1)
    }
}