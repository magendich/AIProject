package com.example.aiproject

import com.example.aiproject.domain.model.CarDetailedInfo
import com.example.aiproject.domain.model.CarModel
import com.example.aiproject.domain.usecase.GetCarDetailedInfoUseCase
import com.example.aiproject.domain.usecase.GetCarModelsUseCase
import com.example.aiproject.presentation.ui.cars.CarsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CarsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getCarModelsUseCase: GetCarModelsUseCase = mock()
    private val getCarDetailedInfoUseCase: GetCarDetailedInfoUseCase = mock()

    private lateinit var viewModel: CarsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads car models into state`() = runTest {
        val mockCarModels = listOf(
            CarModel(brand = "toyota", name = "Camry"),
            CarModel(brand = "toyota", name = "Corolla")
        )
        whenever(getCarModelsUseCase.invoke()).thenReturn(mockCarModels)

        viewModel = CarsViewModel(getCarModelsUseCase, getCarDetailedInfoUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(2, state.size)
        assertEquals("Camry", state[0].model)
        assertEquals("Toyota", state[0].brand)
    }

    @Test
    fun `init capitalizes brand with lowercase first letter`() = runTest {
        val mockCarModels = listOf(
            CarModel(brand = "honda", name = "Accord")
        )
        whenever(getCarModelsUseCase.invoke()).thenReturn(mockCarModels)

        viewModel = CarsViewModel(getCarModelsUseCase, getCarDetailedInfoUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Honda", state[0].brand)
    }

    @Test
    fun `init sets state to empty list when no models are returned`() = runTest {
        whenever(getCarModelsUseCase.invoke()).thenReturn(emptyList())

        viewModel = CarsViewModel(getCarModelsUseCase, getCarDetailedInfoUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.isEmpty())
    }

    @Test
    fun `onCarSelected updates selectedCarDetail with correct data`() = runTest {
        whenever(getCarModelsUseCase.invoke()).thenReturn(emptyList())

        viewModel = CarsViewModel(getCarModelsUseCase, getCarDetailedInfoUseCase)

        val detailedCar = CarDetailedInfo(
            brand = "toyota",
            model = "Camry",
            year = 2020,
            country = "Japan",
            horsepower = 200,
            topSpeedKph = 240,
            transmissionType = "Automatic",
            driveType = "FWD"
        )
        whenever(getCarDetailedInfoUseCase.invoke("toyota", "Camry"))
            .thenReturn(listOf(detailedCar))

        var callbackCalled = false
        viewModel.onCarSelected("toyota", "Camry") {
            callbackCalled = true
        }

        testDispatcher.scheduler.advanceUntilIdle()

        val selected = viewModel.selectedCarDetail.value
        assertNotNull(selected)
        assertEquals("Toyota", selected?.headline)
        assertEquals("Camry, 2020", selected?.subhead)
        assertEquals("Japan", selected?.country)
        assertEquals("200", selected?.horsepower)
        assertEquals("240", selected?.topSpeedKph)
        assertEquals("Automatic", selected?.transmissionType)
        assertEquals("FWD", selected?.driveType)
        assertTrue(callbackCalled)
    }

    @Test
    fun `onCarSelected handles null horsepower and topSpeed`() = runTest {
        whenever(getCarModelsUseCase.invoke()).thenReturn(emptyList())
        viewModel = CarsViewModel(getCarModelsUseCase, getCarDetailedInfoUseCase)

        val detailedCar = CarDetailedInfo(
            brand = "toyota",
            model = "Yaris",
            year = 2018,
            country = "Japan",
            horsepower = null,
            topSpeedKph = null,
            transmissionType = "Manual",
            driveType = "RWD"
        )
        whenever(getCarDetailedInfoUseCase.invoke("toyota", "Yaris"))
            .thenReturn(listOf(detailedCar))

        viewModel.onCarSelected("toyota", "Yaris") {}
        testDispatcher.scheduler.advanceUntilIdle()

        val selected = viewModel.selectedCarDetail.value
        assertNotNull(selected)
        assertEquals("Toyota", selected?.headline)
        assertEquals("Yaris, 2018", selected?.subhead)
        assertEquals("Japan", selected?.country)
        assertNull(selected?.horsepower)
        assertNull(selected?.topSpeedKph)
        assertEquals("Manual", selected?.transmissionType)
        assertEquals("RWD", selected?.driveType)
    }
}
