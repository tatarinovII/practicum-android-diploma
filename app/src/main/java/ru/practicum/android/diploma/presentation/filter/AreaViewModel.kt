package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AreaViewModel(
    private val areaInteractor: AreaInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<AreaUiState>(AreaUiState.Content())
    val uiState: StateFlow<AreaUiState> = _uiState

    val countries = emptyList<String>()
    val regions = emptyList<String>()

    init {
        setCountry()
        setRegion()
    }

    fun setCountry() {
        viewModelScope.launch {
            areaInteractor.setCountry
        }
    }

    fun setRegion() {
        viewModelScope.launch {
            areaInteractor.setRegion
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            areaInteractor.getCountries()
        }
    }

    fun getRegions() {
        areaInteractor.getRegions()
    }

    fun clearCountry() {
        viewModelScope.launch {
            areaInteractor.clearCountry
        }
    }

    fun clearRegion() {
        viewModelScope.launch {
            areaInteractor.clearRegion()
        }
    }
}
