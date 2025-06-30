package com.example.aiproject.presentation.ui.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiproject.domain.usecase.GetCarDetailedInfoUseCase
import com.example.aiproject.domain.usecase.GetCarModelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val getCarModelsUseCase: GetCarModelsUseCase,
    private val getCarDetailedInfoUseCase: GetCarDetailedInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<CarListItem>>(emptyList())
    val state: StateFlow<List<CarListItem>> = _state.asStateFlow()

    private val _selectedCarDetail = MutableStateFlow<CarCardItem?>(null)
    val selectedCarDetail: StateFlow<CarCardItem?> = _selectedCarDetail.asStateFlow()

    init {
        fetchToyotaModels()
    }

    private fun fetchToyotaModels() {
        viewModelScope.launch {
            _state.value = getCarModelsUseCase.invoke().map {
                CarListItem(
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


    fun onCarSelected(brand: String, model: String, onResult: () -> Unit) {
        viewModelScope.launch {
            val carInfo = getCarDetailedInfoUseCase(brand, model).firstOrNull()
            if (carInfo != null) {
                _selectedCarDetail.value = CarCardItem(
                    headline = capitalizeFirstLetter(carInfo.brand),
                    subhead = "${carInfo.model}, ${carInfo.year}",
                    country = carInfo.country,
                    horsepower = carInfo.horsepower?.toString(),
                    topSpeedKph = carInfo.topSpeedKph?.toString(),
                    transmissionType = carInfo.transmissionType,
                    driveType = carInfo.driveType
                )
            }
            onResult()
        }
    }
}