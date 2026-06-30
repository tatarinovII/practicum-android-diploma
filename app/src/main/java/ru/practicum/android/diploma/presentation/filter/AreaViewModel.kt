package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AreaViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(AreaUiState())
    val uiState: StateFlow<AreaUiState> = _uiState

    val countries = emptyList<String>()

    init {
        setCountry()
        setRegion()
    }

    fun setCountry() {

    }

    fun setRegion() {

    }

    fun getCountries() {

    }

    fun getRegions() {

    }

    fun clearCountry() {

    }

    fun clearRegion() {

    }
}
